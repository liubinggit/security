package com.liubing.security.core.social.qq.api;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QQimpl extends AbstractOAuth2ApiBinding implements QQ {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%";

	private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

	private String appId;

	private String openId;

	private ObjectMapper objectMapper = new ObjectMapper();

	public QQimpl(String accessToken, String appId) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		this.appId = appId;

		String result = getRestTemplate().getForObject(URL_GET_OPENID, String.class);
		logger.info(result);

		this.openId = StringUtils.substringBetween(result, "\"openid\":", "}");
	}

	@Override
	public QQUserinfo getUserInfo()  {
		String result = getRestTemplate().getForObject(URL_GET_USERINFO, String.class, appId, openId);
		logger.info(result);
		QQUserinfo qqUserinfo = null;
		try {
			qqUserinfo = objectMapper.readValue(result, QQUserinfo.class);
		} catch (IOException e) {
			throw new RuntimeException("获取用户信息失败");
		}
		logger.info("qqUserinfo:" + qqUserinfo);
		return qqUserinfo;
	}

}
