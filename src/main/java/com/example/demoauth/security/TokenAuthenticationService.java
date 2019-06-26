package com.example.demoauth.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import br.gov.pa.prodepa.controleacesso.client.dto.UsuarioDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	
	// EXPIRATION_TIME = 10 dias
	static final long EXPIRATION_TIME = 860_000_000;
	static final String SECRET = "MySecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	
	static void addAuthentication(HttpServletResponse response, Authentication auth) {
		
		ControleAcessoAuthenticationToken token = (ControleAcessoAuthenticationToken) auth;
		
		String JWT = Jwts.builder()
				.setSubject(token.getUsuarioDto().getLogin())
				//.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.claim("user_id", token.getUsuarioDto().getId())				
				.claim("roles", token.getUsuarioDto().getOperacoes())
				.claim("session_id", token.getUsuarioDto().getPassaporte())
				.compact();
		
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}
	
	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {
			// faz parse do token
			Claims body = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody();
			
			if (body != null) {
				String[] roles = ((List<String>) body.get("roles")).stream().map( r -> "ROLE_" + r).toArray(String[]::new);
				
				List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(roles);
				
				UsuarioDto dto = new UsuarioDto();
				dto.setLogin((String) body.get("sub"));
				dto.setPassaporte((String) body.get("session_id"));
				
				return new ControleAcessoAuthenticationToken(dto, authorityList);
			}
		}
		return null;
	}
	
}