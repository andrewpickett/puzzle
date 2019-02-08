package com.profounddistortion.puzzle.security;

import lombok.Data;

@Data
public class JwtContainer {
	public static final String TOKEN_PREFIX = "Bearer ";

	private String jwtSecretKey;
	private int jwtExpirationTime;

}
