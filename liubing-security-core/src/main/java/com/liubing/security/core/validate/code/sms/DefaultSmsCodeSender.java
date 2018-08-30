package com.liubing.security.core.validate.code.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSmsCodeSender implements SmsCodeSender {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void send(String mobile, String code) {
		log.info("给手机" + mobile + "发送短信短信验证码:" + code);
	}

}
