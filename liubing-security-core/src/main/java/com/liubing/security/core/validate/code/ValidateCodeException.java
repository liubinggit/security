package com.liubing.security.core.validate.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private static final long serialVersionUID = -3398560079331417891L;

	public ValidateCodeException(String msg) {
		super(msg);
		log.info(msg);
	}

}
