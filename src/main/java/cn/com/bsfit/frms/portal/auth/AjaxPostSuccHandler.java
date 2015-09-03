package cn.com.bsfit.frms.portal.auth;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * 登录成功处理
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
public class AjaxPostSuccHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private Logger logger = LoggerFactory.getLogger(AjaxPostSuccHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
			Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response);
        Writer out = response.getWriter();
        out.write("{success:true, targetUrl : \'" + targetUrl + "\'}");
        out.close();
        logger.info("user {} with detail {} login sucessfully!", authentication.getPrincipal(), authentication.getDetails());
		super.onAuthenticationSuccess(request, response, authentication);
	}
}