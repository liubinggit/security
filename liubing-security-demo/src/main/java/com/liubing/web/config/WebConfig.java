package com.liubing.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	/**
	 * 异步rest配置
	 */
	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		//拦截异步的过滤器
//		configurer.registerCallableInterceptors(interceptors);
//		configurer.registerDeferredResultInterceptors(interceptors);
	}

//	@Autowired
//	private TimeInterceptor timeInterceptor;
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(timeInterceptor);
//	}
//
//	@Bean
//	public FilterRegistrationBean timerFilter2() {
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		TimeFilter2 tf2 = new TimeFilter2();
//		registrationBean.setFilter(tf2);
//
//		List<String> urlPatterns = new ArrayList<String>();
//		urlPatterns.add("/*");
//		registrationBean.setUrlPatterns(urlPatterns);
//		return registrationBean;
//	}

}
