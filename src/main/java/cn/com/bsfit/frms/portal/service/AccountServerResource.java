package cn.com.bsfit.frms.portal.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bsfit.frms.portal.base.log.LogDBUtil;
import cn.com.bsfit.frms.portal.base.mapper.UsersMapper;
import cn.com.bsfit.frms.portal.base.mapper.UsersRolesMapper;
import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.pojo.UsersDetail;
import cn.com.bsfit.frms.portal.base.pojo.UsersExample;
import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.base.util.MD5;
import cn.com.bsfit.frms.portal.base.util.SysContent;

/**
 * 我的账号Service
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/account")
public class AccountServerResource {

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UsersRolesMapper usersRolesMapper;

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public ExtJSResponse findAccount() {
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		UsersExample usersExample = new UsersExample();
		usersExample.createCriteria().andIdEqualTo(currentUsers.getId());
		UsersDetail usersDetail = usersMapper.selectUsersDetailByExample(usersExample).get(0);
		if (usersDetail != null) {
			// 角色名,以字符串分割
			String roleNames = usersRolesMapper.selectRoleNamesByUserId(usersDetail.getId());
			usersDetail.setRoleNames(roleNames);
			usersDetail.setPassword(null);
		}
		return ExtJSResponse.successResWithData(usersDetail);
	}

	/**
	 * 编辑账号
	 * 
	 * @param originalPassword
	 * @param newPassword
	 * @param email
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.POST }, consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ExtJSResponse updateAccount(final @ModelAttribute(value = "originalPassword") String originalPassword,
			final @ModelAttribute(value = "newPassword") String newPassword,
			final @ModelAttribute(value = "email") String email,
			final @ModelAttribute(value = "mobile") String mobile) {
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		Users users = usersMapper.selectByPrimaryKey(currentUsers.getId());
		// 当前用户密码
		String userPassword = users.getPassword();
		// 用户输入密码
		String encodedPassword = MD5.md5(originalPassword);
		if (encodedPassword.equals(userPassword)) {
			if (newPassword != null && !"".equals(newPassword)) {
				String newEncodedPassword = MD5.md5(newPassword);
				users.setPassword(newEncodedPassword);
			}
			if (email != null && !"".equals(email)) {
				users.setEmail(email);
			}
			if (mobile != null && !"".equals(mobile)) {
				users.setMobile(mobile);
			}
			users.setUpdateTime(new Date());
			usersMapper.updateByPrimaryKeySelective(users);
			LogDBUtil.info(this.getClass(), "Users[{}] updated by Operator[{}].", users.getUserName(), currentUsers.getUserName());
		} else {
			return ExtJSResponse.errorRes("您输入的密码不正确!");
		}
		return ExtJSResponse.success();
	}
}