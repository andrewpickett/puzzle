package com.profounddistortion.puzzle.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profounddistortion.puzzle.model.ApplicationUser;
import com.profounddistortion.puzzle.security.JwtAuthenticationToken;
import com.profounddistortion.puzzle.security.JwtContainer;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
	private JwtContainer jwtContainer;

	public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, JwtContainer jwtContainer) {
		super(defaultFilterProcessesUrl);
		setAuthenticationManager(authenticationManager);
		this.jwtContainer = jwtContainer;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
		ApplicationUser user = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
		return getAuthenticationManager().authenticate(new JwtAuthenticationToken(user.getName(), user.getPassword(), Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
		response.addHeader(HttpHeaders.WWW_AUTHENTICATE, JwtContainer.TOKEN_PREFIX.trim());
		response.addHeader(HttpHeaders.AUTHORIZATION, JwtAuthenticationToken.buildToken((JwtAuthenticationToken) auth, jwtContainer));
		if (isAdmin((JwtAuthenticationToken) auth)) {
			response.addHeader("X-User-Admin", "true");
		}
	}

	private boolean isAdmin(JwtAuthenticationToken auth) {
		if (auth != null) {
			for (GrantedAuthority ga : auth.getAuthorities()) {
				if (ga.getAuthority().equals("ROLE_ADMIN")) {
					return true;
				}
			}
		}
		return false;
	}
}
