package com.example.demoauth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.example.demoauth.config.AppConfiguration;

import br.gov.pa.prodepa.controleacesso.client.ControleAcessoClient;

public class JWTAuthenticationFilter extends GenericFilterBean {

	private AppConfiguration config;
	
	public JWTAuthenticationFilter() {
	}
	
	public JWTAuthenticationFilter(AppConfiguration config) {
		this.config = config;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		ControleAcessoAuthenticationToken authentication = (ControleAcessoAuthenticationToken) TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
		
		ControleAcessoClient caClient = new ControleAcessoClient(config.getHost(), config.getLogin(), config.getPassword());
		try {
			boolean atualizarAcesso = caClient.atualizarAcesso(config.getApplicationId(), null, authentication.getSessionId());
			
			if(!atualizarAcesso) {
				throw new AuthenticationException("Sessão expirada") {};
			}
		} catch (Exception e) {
			throw new AuthenticationException("Sessão expirada", e) {};
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}