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
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import com.liubing.security.browser.logout.LiubingLogoutSuccessHandler;
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
	
	@Autowired
	private SpringSocialConfigurer liubingSocialSecurityConfig;
	
	@Autowired
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
	
	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy;
	
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;


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
			.and().apply(liubingSocialSecurityConfig)
			.and().sessionManagement()
				.invalidSessionStrategy(invalidSessionStrategy) //session失效跳转地址
				.maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions()) //设置同一用户最大数量
				.maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin()) // 默认false 第二次登陆踢掉第一个用户, 设置为true刚好相反
				.expiredSessionStrategy(sessionInformationExpiredStrategy)// 记录谁登陆
				.and()
			.and()
			.logout()
				.logoutUrl("/signOut")
//				.logoutSuccessUrl("/logout.html")
				.logoutSuccessHandler(logoutSuccessHandler)
			.and().rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(userDetailsService).and().authorizeRequests()
				.antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, //需要身份认证url
						SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,//手机登录
						securityProperties.getBrowser().getLoginPage(),	//url登录
						securityProperties.getBrowser().getSignOutUrl(),	//退出页面
						"/session/invalid",
						SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*") //发送验证码url
				.permitAll().anyRequest().authenticated().and().csrf().disable();

	}
}
