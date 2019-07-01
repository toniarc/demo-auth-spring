package com.example.demoauth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.pa.prodepa.controleacesso.client.dto.AutorizarEnvDto;

public class ControleAcessoLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	public ControleAcessoLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		AutorizarEnvDto dto = new ObjectMapper().readValue(request.getInputStream(), AutorizarEnvDto.class);
		
		if(dto.getPassaporte() == null) {
			throw new AuthenticationException("Sessao expirada") {};
		}

		return getAuthenticationManager().authenticate(new ControleAcessoAuthenticationToken(dto.getPassaporte()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication auth) throws IOException, ServletException {

		TokenAuthenticationService.addAuthentication(response, auth);
	}

}