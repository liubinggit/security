package com.liubing.security.core.social.qq.conn;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import com.liubing.security.core.social.qq.api.QQ;

public class QQConntectionFactory extends OAuth2ConnectionFactory<QQ> {

	public QQConntectionFactory(String providerId, String appId, String appsecret) {
		super(providerId, new QQServiceProvider(appId, appsecret), new QQAdapter());
	}

}
