package com.example.demoauth.security;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class ControleAcessoAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		CookieAuthenticationToken cookieAuthToken = (CookieAuthenticationToken) authentication;
		
		//Efetuar autenticacao
		return new CookieAuthenticationToken("username", "principal", Collections.EMPTY_LIST);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isInstance(CookieAuthenticationToken.class);
	}

}
