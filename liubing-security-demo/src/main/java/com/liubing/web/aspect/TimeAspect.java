package com.liubing.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAspect {
	
	/**
	 * 切入点
	 * 1).方法起作用
	 * 2).什么时候起作用
	 * 
	 * 常用
	 * Before  = 拦截器preHandle
	 * After	= 拦截器afterCompletion
	 * AfterThrowing	= 拦截器postHandle
	 * Around
	 * @throws Throwable 
	 */
	
	@Around("execution(* com.liubing.web.controller.UserController.*(..))")
	public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("TimeAspect handleControllerMethod start");
		Object[] args = pjp.getArgs();
		
		for(Object arg:args) {
			System.out.println("arg :" + arg);
		}
		Long start = System.currentTimeMillis();
		Object object = pjp.proceed();
		System.out.println("TimeAspect handleControllerMethod 耗时:"+(System.currentTimeMillis() - start));
		System.out.println("TimeAspect handleControllerMethod end");
		return object;
	}
	
	
}
