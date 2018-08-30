package com.liubing.security.core.validate.code;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.liubing.security.core.properties.SecurityProperties;
import com.liubing.security.core.validate.code.image.ImageCode;

/**
 * OncePerRequestFilter确保在一次请求只通过一次filter，而不需要重复执行
 * 
 * InitializingBean 初始化时执行
 * 
 * @author lb
 *
 */
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

	private Logger log = LoggerFactory.getLogger(getClass());

	private AuthenticationFailureHandler authenticationFailureHandler;

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	private Set<String> urls = new HashSet<String>();

	private SecurityProperties securityProperties;

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		String[] urlStrs = {};
		if(!"".equals(securityProperties.getCode().getImage().getUrl())) {
			securityProperties.getCode().getImage().getUrl().split(",");
			for (String url : urlStrs) {
				urls.add(url);
			}
		}
		
		urls.add("/authentication/from");

		log.info("afterPropertiesSet urls:" + urls);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("ValidateCodeFilter doFilterInternal");
		log.info("ValidateCodeFilter url:" + request.getRequestURI());
		log.info("ValidateCodeFilter method:" + request.getMethod());
		log.info("ValidateCodeFilter urls:" + urls);

		boolean action = false;
		for (String url : urls) {
			if (antPathMatcher.match(url, request.getRequestURI())) {
				action = true;
			}
		}

		log.info("ValidateCodeFilter 校验url是否需要验证验证码:" + action);
		if (action) {
			try {
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}

		filterChain.doFilter(request, response);

	}

	private void validate(ServletWebRequest request) throws ServletRequestBindingException {

		// 从session中获取生成的图形验证码
		ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request,
				ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
		
		// 请求中的参数imageCode为登录页面中手填的验证码的值
		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
		
		log.info("ValidateCodeFilter imageCode:" + codeInRequest);

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("请输入验证码");
		}

		if ("".equals(codeInRequest)) {
			throw new ValidateCodeException("验证码不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException("验证码不存在");
		}

		if (codeInSession.isExpired()) {
			sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
			throw new ValidateCodeException("验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}
		log.info("ValidateCodeFilter validate 验证通过");
		sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
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

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

}
