package br.com.challenge.household_financial.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationByTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UserRepository repository;
	
	public AuthenticationByTokenFilter(TokenService tokenService, UserRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recoverToken(request);
		boolean valid = tokenService.isTokenValid(token);
		if(valid) {
			authentClient(token);
		}
		filterChain.doFilter(request, response);
	}


	private void authentClient(String token) {
		Long idUser = tokenService.getIdUser(token);
		User user = repository.findById(idUser).get();
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}


	private String recoverToken(HttpServletRequest request) {
		String token  = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}

}
