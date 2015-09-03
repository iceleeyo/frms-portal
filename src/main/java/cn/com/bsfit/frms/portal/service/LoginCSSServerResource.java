package cn.com.bsfit.frms.portal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;

import com.opensymphony.util.ClassLoaderUtil;

/**
 * 登录页面样式和title
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/loginCss")
public class LoginCSSServerResource {
	
	@Value("${portal.css.type:normal}")
	private String portalCssType;
	private static String CSS_DIRECTORY = "static/resources/css/";// 自定义CSS位置
	private static String INDEX_RELATIVE_DIRECTORY = "resources/css/";// CSS相对于html的位置
	
	@RequestMapping(value = "/getLoginCSS", method = { RequestMethod.GET })
	public ExtJSResponse getLoginCSS() {
		String css = "";
		String loginCssName = "login_" + portalCssType + ".css";
		if (ClassLoaderUtil.getResource(CSS_DIRECTORY + loginCssName, this.getClass()) != null) {
			css = INDEX_RELATIVE_DIRECTORY + loginCssName;
		}
		return ExtJSResponse.successResWithData(css);
	}
	
	@RequestMapping(value = "/getLoginTitle", method = { RequestMethod.GET })
	public ExtJSResponse getLoginTitle() {
		String title = "";
		if(portalCssType.equals("aml")) {
			// 反洗钱后台管理系统
			title = "反洗钱后台管理系统";
		} else {
			// 风控后台管理系统
			title = "风控后台管理系统";
		}
		return ExtJSResponse.successResWithData(title);
	}
}