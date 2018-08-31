package com.liubing.security.core.properties;

import com.liubing.security.core.properties.SecurityConstants;

public class BrowserProperties {

	// 默认browser中的html
	private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

	private LoginResponseType loginType = LoginResponseType.JSON;
	
	private int rememberMeSeconds = 3600;

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		System.out.println("设置loginPage:"+loginPage);
		this.loginPage = loginPage;
	}

	public LoginResponseType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginResponseType loginType) {
		System.out.println("设置loginType: "+loginType);
		this.loginType = loginType;
	}

	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}
	
	

}
