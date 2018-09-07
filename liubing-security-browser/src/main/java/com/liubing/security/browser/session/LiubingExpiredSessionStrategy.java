package com.liubing.security.browser.session;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

public class LiubingExpiredSessionStrategy extends AbstractSessionStrategy  implements SessionInformationExpiredStrategy {

	public LiubingExpiredSessionStrategy(String invalidSessionUrl) {
		super(invalidSessionUrl);
	}

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		event.getResponse().setContentType("application/json;charset=utf-8");
		event.getResponse().getWriter().write("并发登陆");
	}

	@Override
	protected boolean isConcurrency() {
		return true;
	}
}
