package com.profounddistortion.puzzle.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.profounddistortion.puzzle.model.ApplicationUser;
import com.profounddistortion.puzzle.repository.UserRepository;

@Service
public class JwtAuthenticationProvider implements AuthenticationProvider {// extends AbstractUserDetailsAuthenticationProvider {
	@Autowired
	private UserRepository userRepo;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtAuthenticationToken tokenIn = (JwtAuthenticationToken) authentication;

		String name = tokenIn.getName();
		String pw = (String) tokenIn.getCredentials();
		ApplicationUser applicationUser = userRepo.findByName(name);
		if (applicationUser == null || !BCrypt.checkpw(pw, applicationUser.getPassword())) {
			throw new UsernameNotFoundException(name);
		}
		String roleName = applicationUser.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
		return new JwtAuthenticationToken(applicationUser.getId(), applicationUser.getName(), applicationUser.getPassword(), Arrays.asList(new SimpleGrantedAuthority(roleName)));
	}

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
