package com.liubing.security.core.validate.code;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * OncePerRequestFilter确保在一次请求只通过一次filter，而不需要重复执行
 * 
 * @author lb
 *
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

	private Logger log = LoggerFactory.getLogger(getClass());

	private AuthenticationFailureHandler authenticationFailureHandler;

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("ValidateCodeFilter doFilterInternal");
		log.info("ValidateCodeFilter url:" + request.getRequestURI());
		log.info("ValidateCodeFilter method:" + request.getMethod());

		if (StringUtils.equals("/authentication/from", request.getRequestURI())
				&& StringUtils.equals("POST", request.getMethod())) {

			try {
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
			}

		}

		filterChain.doFilter(request, response);

	}

	private void validate(ServletWebRequest request) throws ServletRequestBindingException {

		// 从session中获取生成的图形验证码
		ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateController.SESSION_KEY);

		// 请求中的参数imageCode为登录页面中手填的验证码的值
		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
		log.info("ValidateCodeFilter imageCode:" + codeInRequest);
		if ("".equals(codeInRequest)) {
			throw new ValidateCodeException("验证码不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException("验证码不存在");
		}

		if (codeInSession.isExpired()) {
			sessionStrategy.removeAttribute(request, ValidateController.SESSION_KEY);
			throw new ValidateCodeException("验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}
		log.info("ValidateCodeFilter validate 验证通过");
		sessionStrategy.removeAttribute(request, ValidateController.SESSION_KEY);
	}

	public SessionStrategy getSessionStrategy() {
		return sessionStrategy;
	}

	public void setSessionStrategy(SessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}

	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

}
