package com.liubing.security.core.validate.code;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
public class ValidateController {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;

	@GetMapping("/code/{type}")
	public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
			throws Exception {
		log.info(type+"创建验证码");
		validateCodeProcessors.get(type + "CodeProcessor").create(new ServletWebRequest(request, response));
	}

}
