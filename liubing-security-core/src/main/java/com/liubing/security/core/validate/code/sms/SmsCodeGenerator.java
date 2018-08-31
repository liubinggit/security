package com.liubing.security.core.validate.code.sms;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.liubing.security.core.properties.SecurityProperties;
import com.liubing.security.core.properties.SmsCodeProperties;
import com.liubing.security.core.validate.code.ValidateCode;
import com.liubing.security.core.validate.code.ValidateCodeGenerator;

@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public ValidateCode generate(ServletWebRequest request) {
		SmsCodeProperties ssmCodeProperties = securityProperties.getCode().getSms();
		int expireIn = ServletRequestUtils.getIntParameter(request.getRequest(), "expireIn",
				ssmCodeProperties.getExpireIn());
		int length = ServletRequestUtils.getIntParameter(request.getRequest(), "length", ssmCodeProperties.getLength());

		String sRand = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return new ValidateCode(sRand, expireIn);
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

}
