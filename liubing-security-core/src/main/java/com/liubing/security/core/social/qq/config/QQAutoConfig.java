package com.liubing.security.core.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

import com.liubing.security.core.properties.QQProperties;
import com.liubing.security.core.properties.SecurityProperties;
import com.liubing.security.core.social.qq.conn.QQConntectionFactory;

/**
 * @ConditionalOnProperty注解的作用当存在app-id时底下配置才生效.
 * @author lb
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "liubing.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter{
	
	@Autowired
	private SecurityProperties securityProperties;

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		QQProperties qqProperties = securityProperties.getSocial().getQq();
		return new QQConntectionFactory(qqProperties.getProviderId(), qqProperties.getAppId(), qqProperties.getAppSecret());
	}

}
