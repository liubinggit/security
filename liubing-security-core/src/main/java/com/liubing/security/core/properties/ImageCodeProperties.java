package com.liubing.security.core.properties;

public class ImageCodeProperties extends SmsCodeProperties {

	private int width = 67;
	private int height = 23;
	private String url = ""; // 图片提交需要验证的url,多个用,隔开

	public ImageCodeProperties() {
		this.setLength(4);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
