package com.liubing.security.core.social.qq.conn;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.liubing.security.core.social.qq.api.QQ;
import com.liubing.security.core.social.qq.api.QQUserinfo;

public class QQAdapter implements ApiAdapter<QQ>{

	/**
	 * 测试qq服务是否同的
	 * 
	 */
	@Override
	public boolean test(QQ api) {
		return false;
	}

	@Override
	public void setConnectionValues(QQ api, ConnectionValues values) {

		QQUserinfo userinfo = api.getUserInfo();
		values.setDisplayName(userinfo.getNickname());
		values.setImageUrl(userinfo.getFigureurl_qq_1());
		values.setProfileUrl(null);
		values.setProviderUserId(userinfo.getOpenId());
	}

	@Override
	public UserProfile fetchUserProfile(QQ api) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStatus(QQ api, String message) {
		// TODO Auto-generated method stub
		
	}

}
