package cn.com.bsfit.frms.portal.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统版本信息
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/about")
public class AboutServerResource {

	private Logger logger = LoggerFactory.getLogger(AboutServerResource.class);
	
	@RequestMapping(value = "/version", method = { RequestMethod.GET })
	public Map<String, Object> version() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		
		Class<?> clazz = AboutServerResource.class;
		String className = clazz.getSimpleName() + ".class";
		String classPath = clazz.getResource(className).toString();
		if (!classPath.startsWith("jar")) {
			logger.warn("class not in embedded jars, skipping...");
			map.put("err", "version not found.");
			return map;
		}
		String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF";
		Manifest manifest = new Manifest();
		InputStream is = null;
		try {
			is = new URL(manifestPath).openStream();
			manifest.read(is);
			Attributes attributes = manifest.getMainAttributes();
			String title = attributes.getValue("Implementation-Title");
			String build = attributes.getValue("Implementation-Build");
			String version = attributes.getValue("Implementation-Version");
			logger.info("current versoin of frms-portal is {}", version);
            logger.info("current build number of frms-portal is {}", build);
            map.put("title", title);
            map.put("build", build);
            map.put("version", version);
            map.put("year", Calendar.YEAR);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(is != null) {
				is.close();
			}
		}
		return map;
	}
}