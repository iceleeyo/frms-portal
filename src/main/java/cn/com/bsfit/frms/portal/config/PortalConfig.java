package cn.com.bsfit.frms.portal.config;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * Portal配置 主要是 org.springframework.boot.autoconfigure.web.HttpMessageConverters
 * 
 * @author hjp
 * 
 * @since 1.3.0
 *
 */
@Configuration
public class PortalConfig {

	@Bean
	public HttpMessageConverters customConverters() {
		FastJsonHttpMessageConverter fastjson = new FastJsonHttpMessageConverter();
		return new HttpMessageConverters(fastjson);
	}
}
