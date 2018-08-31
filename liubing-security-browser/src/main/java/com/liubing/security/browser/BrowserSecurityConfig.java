package com.liubing.security.browser;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.liubing.security.core.authentication.AbstractChannelSecurityConfig;
import com.liubing.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.liubing.security.core.properties.SecurityConstants;
import com.liubing.security.core.properties.SecurityProperties;
import com.liubing.security.core.validate.code.ValidateCodeSecurityConfig;

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		tokenRepositoryImpl.setDataSource(dataSource);
		// tokenRepositoryImpl.setCreateTableOnStartup(true);
		return tokenRepositoryImpl;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("LoginPage:" + securityProperties.getBrowser().getLoginPage());
		log.info("remember time:" + securityProperties.getBrowser().getRememberMeSeconds());

		applyPasswordAuthenticationConfig(http);
		
		http.apply(validateCodeSecurityConfig)
			.and().apply(smsCodeAuthenticationSecurityConfig)
			.and().rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(userDetailsService).and().authorizeRequests()
				.antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, //需要身份认证url
						SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,//手机登录
						securityProperties.getBrowser().getLoginPage(),	//url登录
						SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*") //发送验证码url
				.permitAll().anyRequest().authenticated().and().csrf().disable();

	}
}
