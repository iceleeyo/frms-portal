package cn.com.bsfit.frms.portal.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bsfit.frms.portal.base.log.LogDBUtil;
import cn.com.bsfit.frms.portal.base.mapper.ResourcesMapper;
import cn.com.bsfit.frms.portal.base.pojo.Resources;
import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.util.Digest;
import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.base.util.SysContent;
import cn.com.bsfit.frms.portal.bo.ResourcesBO;
import cn.com.bsfit.frms.portal.bo.UsersBO;
import cn.com.bsfit.frms.portal.enums.MenuType;
import cn.com.bsfit.frms.portal.util.Constants;
import cn.com.bsfit.frms.portal.util.Node;
import cn.com.bsfit.frms.portal.util.StringUtils;

/**
 * 登录Service
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/login")
public class LoginServerResource {

	@Autowired
	private UsersBO usersBO;
	@Autowired
	private ResourcesBO resourcesBO;
	@Autowired
	private ResourcesMapper resourcesMapper;
	private Logger logger = LoggerFactory.getLogger(LoginServerResource.class);
	
	/**
	 * 登录操作
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ExtJSResponse login(final @ModelAttribute(value = "loginName") String loginName,
			final @ModelAttribute(value = "password") String password) {
		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
			return ExtJSResponse.errorRes("用户名/密码为空!");
		}
		Users users = usersBO.getUsersByNameAndPwd(loginName.trim(), password);
		if (users == null) {
			return ExtJSResponse.errorRes("用户名不存在/密码不正确");
		}
		// 把当前登录信息放到缓存中
		HttpServletResponse httpServletResponse = SysContent.getResponse();
		String encodeValue = Digest.encryptBASE64(users.getUserName());
		String randomKey = String.valueOf(System.currentTimeMillis()) + encodeValue;
		Cookie cookie = new Cookie(Constants.CSF_COOKIE_NAME, randomKey);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		httpServletResponse.addCookie(cookie);
		usersBO.storeUsersCache(randomKey, users, Constants.USER_LIVE_TIME);
		LogDBUtil.info(this.getClass(), "user[{}] login success.", loginName);
		return ExtJSResponse.success();
	}
	
	/**
	 * 退出操作
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout", method = { RequestMethod.GET })
	public ExtJSResponse logout() {
		HttpServletResponse response = SysContent.getResponse();
		HttpServletRequest request = SysContent.getRequest();
		String path = request.getContextPath() + "/" + "login.html";
		String redisKey = SysContent.getCookieValue(Constants.CSF_COOKIE_NAME);
		Cookie cookie = new Cookie(Constants.CSF_COOKIE_NAME, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		try {
			usersBO.removeUsersCache(redisKey);
			response.sendRedirect(path);
		} catch (IOException e) {
			logger.error("清除cookie失败", e);
			throw new RuntimeException("清除cookie失败", e);
		}
		LogDBUtil.info(this.getClass(), "user[{}] logout success.", SysContent.getUser().getUserName());
		return ExtJSResponse.success();
	}
	
	/**
	 * 登录成功以后查找资源
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findMenu", method = { RequestMethod.GET })
	public ExtJSResponse list() throws Exception {
		List<Resources> resourcesList = resourcesMapper.selectByUsersId(SysContent.getUser().getId(), MenuType.ISMENU.getIndex());
		if(resourcesList == null || resourcesList.size() == 0) {
			throw new Exception("当前用户没有可以显示的菜单!");
		}
		List<Node> nodeList = resourcesBO.buildMenuNode(resourcesList);
		List<Node> menuNodeList = resourcesBO.buildListToTree(nodeList);
		logger.info("user[{}] findMenu success.", SysContent.getUser().getUserName());
		return ExtJSResponse.successResWithData(menuNodeList);
	}
}
