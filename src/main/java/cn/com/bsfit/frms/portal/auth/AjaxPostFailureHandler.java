package cn.com.bsfit.frms.portal.auth;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * 登录失败处理
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
public class AjaxPostFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private Logger logger = LoggerFactory.getLogger(AjaxPostFailureHandler.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, 
			AuthenticationException exception) throws IOException, ServletException {
		Writer out = response.getWriter();
        out.write("{success: false, errors: { reason: '" + exception.getLocalizedMessage() + ".' }}");
        out.close();
        logger.warn("user {} login failed becauseof {}.", request.getParameter("j_username"), exception);
        super.onAuthenticationFailure(request, response, exception);
	}
}