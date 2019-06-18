package com.example.demoauth.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CookieAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 7017591146124048561L;
	
	private Object credentials;
	private Object principal;
	private String sessionId;
	
	public CookieAuthenticationToken(String sessionId) {
		super(null);
		this.sessionId = sessionId;
	}

	public CookieAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.credentials = credentials;
		this.principal = principal;
	}
	
	public CookieAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}
	
	public String getSessionId() {
		return this.sessionId;
	}

}
