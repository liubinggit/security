package com.liubing.security.app.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liubing.security.core.properties.LoginResponseType;
import com.liubing.security.core.properties.SecurityProperties;

/**
 * 登录成功跳转
 * @author lb
 *
 */
@Component("liubingAuthenticationHandler")
public class LiubingAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("LoginType: "+securityProperties.getBrowser().getLoginType());
		if(LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(authentication));
		}else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}

}
