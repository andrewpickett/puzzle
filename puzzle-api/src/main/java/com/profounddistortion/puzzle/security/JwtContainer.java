package com.profounddistortion.puzzle.security;

public class JwtContainer {
	public static final String TOKEN_PREFIX = "Bearer ";

	private String jwtSecretKey;
	private int jwtExpirationTime;

	public String getJwtSecretKey() {
		return jwtSecretKey;
	}

	public void setJwtSecretKey(String jwtSecretKey) {
		this.jwtSecretKey = jwtSecretKey;
	}

	public int getJwtExpirationTime() {
		return jwtExpirationTime;
	}

	public void setJwtExpirationTime(int jwtExpirationTime) {
		this.jwtExpirationTime = jwtExpirationTime;
	}
}
