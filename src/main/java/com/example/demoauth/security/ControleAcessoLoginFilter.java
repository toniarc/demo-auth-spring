package com.example.demoauth.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ControleAcessoLoginFilter extends AbstractAuthenticationProcessingFilter {

	public ControleAcessoLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

//		AccountCredentials credentials = new ObjectMapper().readValue(request.getInputStream(),
//				AccountCredentials.class);
		
		String ksessionid = null;
		
		for (int i = 0; i < request.getCookies().length; i++) {
			Cookie cookie = request.getCookies()[i];
			if(cookie.getName().equals("KSESSIONID")) {
				ksessionid = cookie.getValue(); 
			}
		}
		
		if(ksessionid == null) {
			throw new AuthenticationException("Sessao expirada") {};
		} else {
			//ir no controle de acesso
		}

		//return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
		//		credentials.getUsername(), credentials.getPassword(), Collections.emptyList()));
		return getAuthenticationManager().authenticate(new CookieAuthenticationToken(ksessionid));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication auth) throws IOException, ServletException {

		TokenAuthenticationService.addAuthentication(response, auth.getName());
	}

}