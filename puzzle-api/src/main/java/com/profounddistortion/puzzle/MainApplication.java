package com.profounddistortion.puzzle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.profounddistortion.puzzle.security.JwtContainer;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {

	public static void main(String... args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MainApplication.class);
	}

	@Bean
	public JwtContainer jwtContainer(@Value("${jwt.secret.key}") String jwtSecretKey, @Value("${jwt.expire.millis:1800000}") int jwtExpirationTime) {
		JwtContainer jwt = new JwtContainer();
		jwt.setJwtExpirationTime(jwtExpirationTime);
		jwt.setJwtSecretKey(jwtSecretKey);
		return jwt;
	}
}
