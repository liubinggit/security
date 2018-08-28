package com.liubing.security.core.properties;


public class BrowserProperties {

	// 默认browser中的html
	private String loginPage = "/login.html";

	private LoginType loginType = LoginType.JSON;

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		System.out.println("设置loginPage:"+loginPage);
		this.loginPage = loginPage;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		System.out.println("设置loginType: "+loginType);
		this.loginType = loginType;
	}

}
