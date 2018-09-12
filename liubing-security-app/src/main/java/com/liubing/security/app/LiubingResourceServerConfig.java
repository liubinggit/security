package com.liubing.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

import com.liubing.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.liubing.security.core.properties.SecurityConstants;
import com.liubing.security.core.properties.SecurityProperties;

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
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.formLogin()
			.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
			.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
			.successHandler(liubingAuthenticationSuccessHandler)
			.failureHandler(liubingAuthenticationFailureHandler)
			;
		
		http//.apply(validateCodeSecurityConfig)
		//	.and()
		.apply(smsCodeAuthenticationSecurityConfig)
			.and()
		.authorizeRequests()
			.antMatchers(
					SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, //需要身份认证url
					SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,//手机登录
					securityProperties.getBrowser().getLoginPage(),	//url登录
					securityProperties.getBrowser().getSignOutUrl(),	//退出页面
					SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*") //发送验证码url
				.permitAll()
			.anyRequest()
			.authenticated()
			.and()
		.csrf().disable();
	}
}
