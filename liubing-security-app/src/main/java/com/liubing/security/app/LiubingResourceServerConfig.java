package com.liubing.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.liubing.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.liubing.security.core.authorize.AuthorizeConfigManager;
import com.liubing.security.core.properties.SecurityConstants;
import com.liubing.security.core.properties.SecurityProperties;
import com.liubing.security.core.validate.code.ValidateCodeSecurityConfig;

//EnableResourceServer 资源服务器
@Configuration
@EnableResourceServer
public class LiubingResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Autowired
	protected AuthenticationSuccessHandler liubingAuthenticationSuccessHandler;
	
	@Autowired
	protected AuthenticationFailureHandler liubingAuthenticationFailureHandler;
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	
	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.formLogin()
			.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
			.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
			.successHandler(liubingAuthenticationSuccessHandler)
			.failureHandler(liubingAuthenticationFailureHandler)
			;
		
		http.apply(validateCodeSecurityConfig)
		.and()
		.apply(smsCodeAuthenticationSecurityConfig)
			.and()
		.csrf().disable();
		
		authorizeConfigManager.config(http.authorizeRequests());
	}
}
