package com.liubing.security.browser;


import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.liubing.security.core.properties.SecurityProperties;
import com.liubing.security.core.validate.code.ValidateCodeFilter;


@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	private  Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private AuthenticationSuccessHandler liubingAuthenticationHandler;
	
	@Autowired
	private AuthenticationFailureHandler liubingAuthenticationFailureHandler;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new  BCryptPasswordEncoder();
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		tokenRepositoryImpl.setDataSource(dataSource);
//		tokenRepositoryImpl.setCreateTableOnStartup(true);
		return tokenRepositoryImpl;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("LoginPage:"+securityProperties.getBrowser().getLoginPage());
		log.info("remember time:" + securityProperties.getBrowser().getRememberMeSeconds());
		
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		validateCodeFilter.setAuthenticationFailureHandler(liubingAuthenticationFailureHandler);
		validateCodeFilter.setSecurityProperties(securityProperties);
		validateCodeFilter.afterPropertiesSet();
		
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
				.formLogin()
					.loginPage("/authentication/require")
					.loginProcessingUrl("/authentication/from") //重新设置请求路径
					.successHandler(liubingAuthenticationHandler)
					.failureHandler(liubingAuthenticationFailureHandler)
				.and()
					.rememberMe()
					.tokenRepository(persistentTokenRepository())
					.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
					.userDetailsService(userDetailsService)
				.and()
					.authorizeRequests() // 下面授权的配置
					.antMatchers("/authentication/require","/code/*",
							securityProperties.getBrowser().getLoginPage()).permitAll() //登录页面可以不用权限登录
					.anyRequest() // 任何请求
					.authenticated()// 都需要身份认证
				.and()
				.csrf().disable(); //暂时先停止csrf
	}
}
