package com.liubing.security.core.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.liubing.security.core.properties.SecurityConstants;

/**
 * 登录信息抽象出来
 * @author lb
 *
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	protected AuthenticationSuccessHandler liubingAuthenticationSuccessHandler;
	
	@Autowired
	protected AuthenticationFailureHandler liubingAuthenticationFailureHandler;
	
	protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
			.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
			.successHandler(liubingAuthenticationSuccessHandler)
			.failureHandler(liubingAuthenticationFailureHandler);
	}
}
