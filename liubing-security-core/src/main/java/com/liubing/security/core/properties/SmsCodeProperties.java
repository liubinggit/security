package com.liubing.security.core.properties;

public class SmsCodeProperties {

	private int length = 6;
	private int expireIn = 60;
	private String url = ""; // 图片提交需要验证的url,多个用,隔开

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
