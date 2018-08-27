package com.liubing.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 模拟第三方的过滤器, 少了@Component
 * @author lb
 *
 */
public class TimeFilter2 implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("TimeFilter2 init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("TimeFilter2 doFilter");
		System.out.println("url:" + ((HttpServletRequestWrapper)request).getRequestURI());
		long start = System.currentTimeMillis();
		chain.doFilter(request, response);
		System.out.println("TimeFilter2 time:"+(System.currentTimeMillis() - start));
	}

	@Override
	public void destroy() {
		System.out.println("TimeFilter2 destroy");
	}

}
