//package cn.com.bsfit.frms.portal.security;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
//
//import cn.com.bsfit.frms.portal.auth.AjaxPostFailureHandler;
//import cn.com.bsfit.frms.portal.auth.AjaxPostSuccHandler;
//
///**
// * Spring Security配置
// * 
// * @author hjp
// * 
// * @since 1.0.0
// *
// */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//	
//	@Configuration
//	@Order(1)
//	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//		
//		@Autowired
//		@Qualifier("rulesDataSource")
//		private DataSource rulesDataSource;
//
//		protected void configure(HttpSecurity http) throws Exception {
//			http.csrf().disable().headers().disable().authorizeRequests().antMatchers(HttpMethod.HEAD, "/rs/**")
//				.permitAll().antMatchers("/rs/**", "/", "/index.html").authenticated().and()
//				.requestMatcher(new RequestHeaderRequestMatcher("User-Agent", "java")).httpBasic()
//				.realmName("frms-rules").and().logout()
//				.logoutUrl("/j_spring_security_logout")
//				.logoutSuccessUrl("/login.html");
//		}
//
//		@Override
//		public void configure(AuthenticationManagerBuilder auth) throws Exception {
//			auth.jdbcAuthentication().dataSource(rulesDataSource).passwordEncoder(new ShaPasswordEncoder(256))
//				.authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username=?")
//				.groupAuthoritiesByUsername("SELECT ga.group_name, ga.group_name, ga.authority FROM group_authorities ga " 
//						+ "INNER JOIN (SELECT username, group_name FROM users WHERE username=?) aga  ON (ga.group_name=aga.group_name)")
//				.usersByUsernameQuery("select USERNAME, PASSWORD, ENABLED  from USERS where USERNAME = ?");
//		}
//	}
//
//	@Configuration
//	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
//
//		@Autowired
//		@Qualifier("rulesDataSource")
//		private DataSource rulesDataSource;
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			AjaxPostSuccHandler succHandler = new AjaxPostSuccHandler();
//			succHandler.setDefaultTargetUrl("/index.html");
//			http.csrf().disable().headers().disable().authorizeRequests()
//					.antMatchers(HttpMethod.HEAD, "/rs/**").permitAll()
//					.antMatchers("/rs/**", "/", "/index.html").authenticated()
//					.and().formLogin().loginPage("/login.html")
//					.usernameParameter("j_username")
//					.passwordParameter("j_password")
//					.loginProcessingUrl("/j_spring_security_check")
//					.defaultSuccessUrl("/index.html")
//					.failureHandler(new AjaxPostFailureHandler())
//					.successHandler(succHandler).permitAll().and().logout()
//					.logoutUrl("/j_spring_security_logout")
//					.logoutSuccessUrl("/login.html").permitAll();
//		}
//
//		@Override
//		public void configure(AuthenticationManagerBuilder auth) throws Exception {
//			auth.jdbcAuthentication().dataSource(rulesDataSource).passwordEncoder(new ShaPasswordEncoder(256))
//				.authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username=?")
//				.groupAuthoritiesByUsername("SELECT ga.group_name, ga.group_name, ga.authority FROM group_authorities ga " 
//						+ "INNER JOIN (SELECT username, group_name FROM users WHERE username=?) aga  ON (ga.group_name=aga.group_name)")
//				.usersByUsernameQuery("select USERNAME, PASSWORD, ENABLED  from USERS where USERNAME = ?");
//		}
//	}
//}