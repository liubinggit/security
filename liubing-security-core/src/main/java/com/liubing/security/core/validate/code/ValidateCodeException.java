package com.liubing.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

	private static final long serialVersionUID = -3398560079331417891L;

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
