/**
 * 
 */
package com.liubing.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.liubing.security.core.properties.SecurityConstants;
import com.liubing.security.core.properties.SecurityProperties;

@Component
@Order(Integer.MIN_VALUE)
public class LiubingAuthorizeConfigProvider implements AuthorizeConfigProvider {

	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.antMatchers(
				SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, //需要身份认证url
				SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,//手机登录
				securityProperties.getBrowser().getLoginPage(),	//url登录
				securityProperties.getBrowser().getSignOutUrl(),	//退出页面
				"/session/invalid",
				SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*"
				)
		.permitAll();
	}

}
