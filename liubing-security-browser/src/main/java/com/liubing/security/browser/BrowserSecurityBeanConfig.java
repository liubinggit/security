/**
 * 
 */
package com.liubing.security.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.liubing.security.browser.logout.LiubingLogoutSuccessHandler;
import com.liubing.security.browser.session.LiubingExpiredSessionStrategy;
import com.liubing.security.browser.session.LiubingInvalidSessionStrategy;
import com.liubing.security.core.properties.SecurityProperties;

/**
 * @author lb
 *
 */
@Configuration
public class BrowserSecurityBeanConfig {
	@Autowired
	private SecurityProperties securityProperties;

	@Bean
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy() {
		return new LiubingInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
	}

	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
		return new LiubingExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
	}
	
	@Bean
	@ConditionalOnMissingBean(LiubingLogoutSuccessHandler.class)
	public LiubingLogoutSuccessHandler liubingLogoutSuccessHandler() {
		return new LiubingLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
	}
}
