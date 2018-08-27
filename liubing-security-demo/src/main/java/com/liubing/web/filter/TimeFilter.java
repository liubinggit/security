package com.liubing.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.stereotype.Component;

@Component
public class TimeFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("TimeFilter init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("TimeFilter doFilter");
		System.out.println(((HttpServletRequestWrapper)request).getRequestURI());
		long start = System.currentTimeMillis();
		chain.doFilter(request, response);
		System.out.println("TimeFilter time:"+(System.currentTimeMillis() - start));
	}

	@Override
	public void destroy() {
		System.out.println("TimeFilter destroy");
	}

}
