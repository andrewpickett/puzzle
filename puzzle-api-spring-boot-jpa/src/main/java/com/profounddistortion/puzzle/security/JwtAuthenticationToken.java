package com.profounddistortion.puzzle.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 6852676492240653239L;

	private static final String ID_CLAIM = "userId";
	private static final String ROLE_CLAIM = "role";
	private static final String ISSUER = "ProfoundDistortion";

	private String token;
	private int id;

	public JwtAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

	public JwtAuthenticationToken(int id, Object principal, Object credentials) {
		super(principal, credentials);
		this.id = id;
	}

	public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	public JwtAuthenticationToken(int id, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.id = id;
	}

	public static String buildToken(JwtAuthenticationToken auth, JwtContainer jwt) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(ID_CLAIM, auth.getId());
		claims.put(ROLE_CLAIM, auth.getAuthorities() == null ? "" : auth.getAuthorities().iterator().next().getAuthority());
		String jwtToken = Jwts.builder()
			.setClaims(claims)
			.setSubject(auth.getName())
			.setIssuedAt(new Date())
			.setIssuer(ISSUER)
			.setExpiration(DateUtils.addMilliseconds(new Date(), jwt.getJwtExpirationTime()))
			.signWith(SignatureAlgorithm.HS512, jwt.getJwtSecretKey())
			.compact();
		return jwtToken;
	}

	public static JwtAuthenticationToken parse(String authToken, JwtContainer jwt) {
		JwtAuthenticationToken userToken = null;
		try {
			Claims claims = Jwts.parser()
				.setSigningKey(jwt.getJwtSecretKey())
				.parseClaimsJws(authToken)
				.getBody();

			if (claims != null && claims.getExpiration().after(new Date())) {
				userToken = new JwtAuthenticationToken(claims.get(ID_CLAIM, Integer.class), claims.getSubject(), null,
					AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get(ROLE_CLAIM, String.class)));
				userToken.setToken(authToken);
			}
		} catch (SignatureException e) {
		} catch (ExpiredJwtException e) {
		}
		return userToken;
	}
}
