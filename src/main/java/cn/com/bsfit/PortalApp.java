package cn.com.bsfit;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class PortalApp {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder().showBanner(false).sources(PortalApp.class).run(args);
	}
}