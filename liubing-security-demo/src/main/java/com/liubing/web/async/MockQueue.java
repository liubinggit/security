package com.liubing.web.async;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MockQueue {
	
	private static final Logger log = Logger.getLogger(MockQueue.class);
	
	private String pacleOrder; //接收下单的请求
	
	private String completeOrder; //下单完成

	public String getPacleOrder() {
		return pacleOrder;
	}

	public void setPacleOrder(String pacleOrder) throws InterruptedException {
		new Thread(() -> {
			log.info("接到下单的请求");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.completeOrder = pacleOrder;
			log.info("下单请求处理完毕, " + pacleOrder);
		}).start();
	}

	public String getCompleteOrder() {
		return completeOrder;
	}

	public void setCompleteOrder(String completeOrder) {
		this.completeOrder = completeOrder;
	}
	
}
