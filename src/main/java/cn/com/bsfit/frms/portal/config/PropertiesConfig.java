package cn.com.bsfit.frms.portal.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import cn.com.bsfit.frms.portal.bo.PropertiesPathBO;

/**
 * Spring 支持多个配置文件
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@Configuration
public class PropertiesConfig {
	
	@Autowired
	private List<PropertiesPathBO> paths;

	@Bean
	public PropertiesFactoryBean propertiesFactory() throws IOException {
		if (paths != null && paths.size() > 0) {
			PropertiesFactoryBean bean = new PropertiesFactoryBean();
			bean.setFileEncoding("UTF-8");
			List<ClassPathResource> resources = new ArrayList<ClassPathResource>(paths.size());
			for (PropertiesPathBO path : paths) {
				if (StringUtils.isNotEmpty(path.getPropertiesClassPath())) {
					resources.add(new ClassPathResource(path.getPropertiesClassPath()));
				}
			}
			bean.setLocations(resources.toArray(new ClassPathResource[resources.size()]));
			bean.afterPropertiesSet();
			return bean;
		}
		return null;
	}
}