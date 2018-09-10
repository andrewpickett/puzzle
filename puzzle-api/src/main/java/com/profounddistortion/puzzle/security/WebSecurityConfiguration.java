package com.profounddistortion.puzzle.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.profounddistortion.puzzle.security.filter.JwtAuthenticationFilter;
import com.profounddistortion.puzzle.security.filter.JwtLoginFilter;
import org.springframework.security.web.header.Header;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtAuthenticationProvider authenticationProvider;
	@Resource(name="jwtContainer")
	private JwtContainer jwtContainer;

	public WebSecurityConfiguration() {
		super(true);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.cors()
			.and().csrf().disable()
			.anonymous()
			.and().authorizeRequests()
				//.antMatchers("/").permitAll()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated()
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.addFilterBefore(new JwtLoginFilter("/login", authenticationManager(), jwtContainer), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JwtAuthenticationFilter(jwtContainer), UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
//				response.sendRedirect("/login");
			});
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Bean
	@Profile("dev")
	public WebMvcConfigurer corsConfigurer() {
		// TODO: Tighten this up later.
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedHeaders("*")
						.allowedMethods("*")
						.allowCredentials(true)
						.exposedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.WWW_AUTHENTICATE, "X-User-Admin");
			}
		};
	}
}
