package com.liubing.security.core.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ValidateCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2148158194694760115L;
	private String code; //验证码里面字符串
	private LocalDateTime expireTime; //图片有效期
	

	public ValidateCode(String code, LocalDateTime expireTime) {
		this.code = code;
		this.expireTime = expireTime;
	}

	public ValidateCode(String code, int expireIn) {
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expireTime);
	}

}
