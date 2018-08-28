package com.liubing.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "liubing.security")
public class SecurityPeoperties {

	private BrowserProperties browser = new BrowserProperties();

	public BrowserProperties getBrowserProperties() {
		return browser;
	}

	public void setBrowserProperties(BrowserProperties browser) {
		this.browser = browser;
	}

}
