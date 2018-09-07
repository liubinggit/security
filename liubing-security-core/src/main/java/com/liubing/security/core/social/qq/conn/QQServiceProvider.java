package com.liubing.security.core.social.qq.conn;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

import com.liubing.security.core.social.qq.api.QQ;
import com.liubing.security.core.social.qq.api.QQimpl;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

	private String appId;

	private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

	private static final String URL_ACCESS_TOKEM = "https://graph.qq.com/oauth2.0/token";

	public QQServiceProvider(String appId, String appsecret) {
		super(new OAuth2Template(appId, appsecret, URL_AUTHORIZE, URL_ACCESS_TOKEM));
	}

	@Override
	public QQ getApi(String accessToken) {
		return new QQimpl(accessToken, appId);
	}

}
