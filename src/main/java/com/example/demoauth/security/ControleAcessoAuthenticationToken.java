package com.example.demoauth.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import br.gov.pa.prodepa.controleacesso.client.dto.UsuarioDto;

public class ControleAcessoAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 7017591146124048561L;
	
	private Object credentials;
	private Object principal;
	private String sessionId;
	private Long idAplicacao;
	private UsuarioDto usuarioDto;
	
	public ControleAcessoAuthenticationToken(String sessionId) {
		super(null);
		this.sessionId = sessionId;
	}

	public ControleAcessoAuthenticationToken(UsuarioDto usuarioDto , Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.usuarioDto = usuarioDto;
		this.principal = usuarioDto.getLogin();
		this.sessionId = usuarioDto.getPassaporte();
		this.setAuthenticated(true);
	}
	
	public ControleAcessoAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
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

	public Long getIdAplicacao() {
		return idAplicacao;
	}
	
	public UsuarioDto getUsuarioDto() {
		return usuarioDto;
	}
}
