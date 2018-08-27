package com.liubing.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.liubing.exception.UserNoExistException;

/**
 * 处理其他控制器发出的异常
 * @author lb
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(UserNoExistException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleUserNoExistException(UserNoExistException ex){
		Map<String, Object> map = new HashMap<>();
		map.put("id", ex.getId());
		map.put("message", ex.getMessage());
		System.out.println("ControllerExceptionHandler handleUserNoExistException");
		return map;
	}

}
