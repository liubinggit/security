package com.liubing.web.async;


import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class AsyncController {
	
	@Autowired
	private MockQueue mockQueue;
	@Autowired
	private DeferredResultHolder deferredResultHolder;
	
	private static final Logger log = Logger.getLogger(AsyncController.class);
	
	/**
	 * 一般使用主线程
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping("/order")
	public String getOrder() throws InterruptedException {
		log.info("主线程开启");
		Thread.sleep(1000);
		log.info("主线程返回");
		return "success";
	}

	/**
	 * 使用runnable异步处理rest服务
	 * 适用于简单的异步逻辑
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping("/order2")
	public Callable<String> getOrder2() throws InterruptedException {
		log.info("主线程开启");
		Callable<String> result = new Callable<String>(){
			@Override
			public String call() throws Exception {
				log.info("副线程开启");
				Thread.sleep(1000);
				log.info("副线程结束");
				return "success";
			}
		};
		log.info("主线程返回");
		return result;
	}
	
	/**
	 * 使用deferredResult异步处理rest服务
	 * 两台或多台的服务器(两个是完全隔离的,使用消息队列沟通)
	 * 
	 * 1.消息队列 (暂时用对象模拟)
	 * 2.线程1的controller处理
	 * 3.监听器
	 * 4.deferredResult
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping("/order3")
	public DeferredResult<String> getOrder3() throws InterruptedException {
		log.info("主线程开启");
		mockQueue.setPacleOrder("11");
		DeferredResult<String> result = new DeferredResult<>();
		deferredResultHolder.getMap().put("11", result);
		log.info("主线程返回");
		return result;
	}
}
