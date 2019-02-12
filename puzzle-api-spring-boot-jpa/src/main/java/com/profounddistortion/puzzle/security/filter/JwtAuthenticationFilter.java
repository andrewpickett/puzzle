package com.profounddistortion.puzzle.security.filter;

import com.profounddistortion.puzzle.security.JwtAuthenticationToken;
import com.profounddistortion.puzzle.security.JwtContainer;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private JwtContainer jwtContainer;

	public JwtAuthenticationFilter(JwtContainer jwtContainer) {
		this.jwtContainer = jwtContainer;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (header != null && header.startsWith(JwtContainer.TOKEN_PREFIX)) {
			String authToken = header.replaceAll(JwtContainer.TOKEN_PREFIX, "");
			JwtAuthenticationToken authRequest = JwtAuthenticationToken.parse(authToken, jwtContainer);
			if (authRequest != null) {
				SecurityContextHolder.getContext().setAuthentication(authRequest);
			}
		}
		filterChain.doFilter(request, response);
	}
}
