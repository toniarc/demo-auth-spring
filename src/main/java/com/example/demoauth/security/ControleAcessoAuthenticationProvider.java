package com.example.demoauth.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.example.demoauth.config.AppConfiguration;

import br.gov.pa.prodepa.controleacesso.client.ControleAcessoClient;
import br.gov.pa.prodepa.controleacesso.client.dto.UsuarioDto;

@Component
public class ControleAcessoAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private AppConfiguration config;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		ControleAcessoAuthenticationToken cookieAuthToken = (ControleAcessoAuthenticationToken) authentication;
		
		ControleAcessoClient caClient = new ControleAcessoClient(config.getHost(), config.getLogin(), config.getPassword());
		UsuarioDto usuarioDto = null;
		
		try {
			usuarioDto = caClient.autorizar(config.getApplicationId()	, cookieAuthToken.getSessionId());
			String[] roles = usuarioDto.getOperacoes().stream().map( r -> "ROLE_" + r).toArray(String[]::new);
			List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(roles);
			return new ControleAcessoAuthenticationToken(usuarioDto, authorityList);
		} catch (Exception e) {
			throw new AuthenticationException("Ocorreram problemas", e) {};
		}
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(ControleAcessoAuthenticationToken.class);
	}

}
