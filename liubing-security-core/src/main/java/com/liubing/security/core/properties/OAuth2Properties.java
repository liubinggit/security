/**
 * 
 */
package com.liubing.security.core.properties;

/**
 * @author lb
 *
 */
public class OAuth2Properties {
	private OAuth2ClientProperties[] clients = {};

	public OAuth2ClientProperties[] getClients() {
		return clients;
	}

	public void setClients(OAuth2ClientProperties[] clients) {
		this.clients = clients;
	}
}
