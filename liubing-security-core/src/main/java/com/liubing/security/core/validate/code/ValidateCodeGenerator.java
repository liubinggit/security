package com.liubing.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

import com.liubing.security.core.validate.code.image.ImageCode;

public interface ValidateCodeGenerator {

	public ValidateCode generate(ServletWebRequest request);

}
