package com.capstone.service;

import com.capstone.security.JwtAuthenticationEntryPoint;
import com.capstone.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private JwtAuthenticationEntryPoint point;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable()) // Enable CORS if needed
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // Specific endpoint access for EMPLOYEE
                .requestMatchers(HttpMethod.GET, "/api/employees/{id}").hasAnyRole("EMPLOYEE", "ADMIN")
                .requestMatchers("/api/employees/department/{id}", 
                                 "/api/employees/tasks/{id}", 
                                 "/api/employees/performance/{id}").hasAnyRole("EMPLOYEE", "ADMIN")
                // Restrict /api/employees to ADMIN only
                .requestMatchers(HttpMethod.POST, "/api/employees/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/employees/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/employees/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("ADMIN")
                .anyRequest().hasRole("ADMIN")
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(point)) // Use JwtAuthenticationEntryPoint for handling authentication errors
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set session management to stateless
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
