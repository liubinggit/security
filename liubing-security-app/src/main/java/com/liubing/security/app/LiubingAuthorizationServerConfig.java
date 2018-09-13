package com.liubing.security.app;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.liubing.security.core.properties.OAuth2ClientProperties;
import com.liubing.security.core.properties.SecurityProperties;


//EnableAuthorizationServer 认证服务器
@Configuration
@EnableAuthorizationServer
public class LiubingAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
			.userDetailsService(userDetailsService)
			.tokenStore(tokenStore);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
		if(ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
			for (OAuth2ClientProperties config : securityProperties.getOauth2().getClients()) {
				
				logger.info("配置oauth2client的信息{}", config);
				builder.withClient(config.getClientId())
				.secret(config.getClientSecret())
				.accessTokenValiditySeconds(config.getAccessTokenValidateSeconds())
				.authorizedGrantTypes("refresh_token", "password")
				.scopes("all", "read", "write");
			}
		}
		
		//可以再用于其他的client  .withClient()....
	}
}
