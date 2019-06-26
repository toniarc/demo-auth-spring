package com.example.demoauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfiguration {

	@Value("${prodepa.controleacesso.ws.host}" )
	private String host;
	
	@Value("${prodepa.controleacesso.ws.login}" )
	private String login;
	
	@Value("${prodepa.controleacesso.ws.password}" )
	private String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
