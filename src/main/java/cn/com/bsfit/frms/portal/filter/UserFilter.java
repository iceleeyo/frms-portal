package cn.com.bsfit.frms.portal.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.com.bsfit.frms.portal.base.log.LogDBUtil;
import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.util.SysContent;
import cn.com.bsfit.frms.portal.bo.UsersBO;
import cn.com.bsfit.frms.portal.util.Constants;

/**
 * 主要用于处理请求，转发和重定向
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@Configuration
@WebFilter(initParams = { @WebInitParam(name = "encoding", value = "UTF-8") }, filterName = "userFilter", urlPatterns = { "/*" })
public class UserFilter extends OncePerRequestFilter {

	@Autowired
	private UsersBO usersBO;
	// 不过滤的URI
	private final String[] notFilter = new String[] { "/login.html", "/login/login", "/loginCss/", ".js", ".css", ".png", ".ico" };

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		// 请求的URI
		String uri = request.getRequestURI();
		// 是否过滤
		boolean doFilter = true;
		for (String s : notFilter) {
			if (uri.indexOf(s) != -1) {
				// 如果URI中包含不过滤的URI,则不进行过滤
				doFilter = false;
				break;
			}
		}
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 防止重入.
		request.setAttribute(getClass().getName(), Boolean.TRUE);
		// 执行过滤
		if (doFilter) {
			// 从cookie中获取登录信息
			String randomKey = SysContent.getCookieValue(Constants.CSF_COOKIE_NAME);
			if (randomKey == null || randomKey.isEmpty()) {
				// 如果cookie中不存在登录信息,则重新登录
				response.sendRedirect(request.getContextPath() + "/login.html");
			} else {
				// 从缓存中获取登录用户信息
				usersBO.expireUsersCache(randomKey, Constants.USER_LIVE_TIME);
				Object object = usersBO.getUsersCache(randomKey);
				if(object != null && object instanceof Users) {
					// 缓存中登录用户信息不为空，将登录用户信息设置到ThreadLocal
					SysContent.setUser((Users) object);
					filterChain.doFilter(request, response);
				} else {
					// 如果缓存中信息为空,重定向到登录页面
					LogDBUtil.error(UserFilter.class, "The user information in the cache is empty!");
					response.sendRedirect(request.getContextPath() + "/login.html");
				}
			}
		} else {
			// 如果不执行过滤，则继续
			filterChain.doFilter(request, response);
		}
	}
}
