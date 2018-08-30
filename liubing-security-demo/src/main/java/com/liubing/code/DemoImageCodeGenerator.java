package com.liubing.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.ServletWebRequest;

import com.liubing.security.core.validate.code.ValidateCode;
import com.liubing.security.core.validate.code.ValidateCodeGenerator;

//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {
	

	private Logger log = LoggerFactory.getLogger(getClass());
			
	@Override
	public ValidateCode generate(ServletWebRequest request) {
		log.info("更换新的图形验证码");
		return null;
	}

}
