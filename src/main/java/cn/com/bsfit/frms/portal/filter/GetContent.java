package cn.com.bsfit.frms.portal.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;

import cn.com.bsfit.frms.portal.base.util.SysContent;

/**
 * 主要用于设置request和response
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@Configuration
@WebFilter(filterName = "storeContent", urlPatterns = { "/*" })
public class GetContent implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 设置request和response的字符集,防止乱码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		SysContent.setRequest((HttpServletRequest) request);
		SysContent.setResponse((HttpServletResponse) response);
		chain.doFilter(request, response);
	}

	public void destroy() {

	}
}
