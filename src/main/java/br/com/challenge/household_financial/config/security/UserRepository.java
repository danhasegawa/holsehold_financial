package br.com.challenge.household_financial.config.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail (String email);

}
