package com.capstone.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig {
	
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		UserDetails user1 = User.builder().
                username("Akshay@gmail.com")
                .password(passwordEncoder().encode("Akshay")).roles("ADMIN").
                build();
		
		UserDetails user2 = User.builder().
                username("Dhondge@gmail.com")
                .password(passwordEncoder().encode("Dhondge")).roles("EMPLOYEE").
                build();
		
		UserDetails user3 = User.builder().
                username("Saisa@gmail.com")
                .password(passwordEncoder().encode("Saisa")).roles("EMPLOYEE").
                build();
		

		UserDetails user4 = User.builder().
                username("uday@gmail.com")
                .password(passwordEncoder().encode("uday")).roles("ADMIN").
                build();
		
		
        return new InMemoryUserDetailsManager(user1,user2,user3);
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
	
}

	
}