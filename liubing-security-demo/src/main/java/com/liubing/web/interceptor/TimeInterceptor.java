package com.liubing.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component
public class TimeInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("TimeInterceptor preHandle start");
		request.setAttribute("startTime", System.currentTimeMillis());

		System.out.println(((HandlerMethod) handler).getBean().getClass().getName());
		System.out.println(((HandlerMethod) handler).getMethod().getName());
		System.out.println("TimeInterceptor preHandle end");
		return true;
	}

	/**
	 * 抛出异常方法不会进入
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("TimeInterceptor postHandle start");
		Long start = (Long) request.getAttribute("startTime");
		System.out.println("TimeInterceptor postHandle 耗时:" + (System.currentTimeMillis() - start));
		System.out.println("TimeInterceptor postHandle end");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
		System.out.println("TimeInterceptor afterCompletion");
		Long start = (Long) request.getAttribute("startTime");
		System.out.println(" TimeInterceptorafterCompletion 耗时:" + (System.currentTimeMillis() - start));
		System.out.println(" TimeInterceptorafterCompletion exception:" + exception);
	}

}
