package com.liubing.security.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.liubing.security.core.properties.SecurityPeoperties;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityPeoperties securityPeoperties;
	
	@Autowired
	private AuthenticationSuccessHandler liubingAuthenticationHandler;
	
	@Autowired
	private AuthenticationFailureHandler liubingAuthenticationFailureHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new  BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("LoginPage:"+securityPeoperties.getBrowserProperties().getLoginPage());
		
		http.formLogin()
				.loginPage("/authentication/require")
				.loginProcessingUrl("/authentication/from") //重新设置请求路径
				.successHandler(liubingAuthenticationHandler)
				.failureHandler(liubingAuthenticationFailureHandler)
				.and()
				.authorizeRequests() // 下面授权的配置
				.antMatchers("/authentication/require",
						securityPeoperties.getBrowserProperties().getLoginPage()).permitAll() //登录页面可以不用权限登录
				.anyRequest() // 任何请求
				.authenticated()// 都需要身份认证
				.and()
				.csrf().disable(); //暂时先停止csrf
	}
}
