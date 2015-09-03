package cn.com.bsfit.frms.portal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;

import com.opensymphony.util.ClassLoaderUtil;

/**
 * Portal动态样式
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/cssConfig")
public class CSSServerResource {

	@Value("${portal.css.module:portal}")
	private String module;// 整体的样式风格

	@Value("${portal.css.style:classic}")
	private String cssStyle;// 整体的样式风格

	private static String CSS_DIRECTORY = "static/resources/css/";// 自定义CSS位置
	private static String INDEX_RELATIVE_DIRECTORY = "resources/css/";// CSS相对于html的位置
	
	/**
	 * 获取要加载的样式
	 * 
	 * @return
	 */
	@RequestMapping(value = "/css", method = { RequestMethod.GET })
	public ExtJSResponse getCSS() {
		String css = "";
		String[] arr = module.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i] + '-' + cssStyle + "-all.css";
		}
		for (String string : arr) {
			if (ClassLoaderUtil.getResource(CSS_DIRECTORY + string, this.getClass()) != null) {
				list.add(string);
			}
		}
		for (String string : list) {
			css += INDEX_RELATIVE_DIRECTORY + string + ",";
		}
		if (css.length() > 0) {
			if (css.substring(css.length() - 1).equals(",")) {
				css = css.substring(0, css.length() - 1);
			}
		}
		return ExtJSResponse.successResWithData(css);
	}
}