package com.liubing.security.core.authentication.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;


@Component
public class SmsCodeAuthenticationSecurityConfig
		extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AuthenticationSuccessHandler liubingAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler liubingAuthenticationFailureHandler;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		log.info("configure");
		
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
		smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(liubingAuthenticationSuccessHandler);
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(liubingAuthenticationFailureHandler);
		
		SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
		smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
		
		http.authenticationProvider(smsCodeAuthenticationProvider)
			.addFilterBefore(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
