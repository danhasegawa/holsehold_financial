package br.com.challenge.household_financial.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}")
	private String expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;

	public String generateToken(Authentication authentication) {
		User login = (User) authentication.getPrincipal();
		Date today = new Date();
		Date dateExpire = new Date(today.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API household")
				.setSubject(login.getId().toString())
				.setIssuedAt(today)
				.setExpiration(dateExpire)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
		
	}

}
