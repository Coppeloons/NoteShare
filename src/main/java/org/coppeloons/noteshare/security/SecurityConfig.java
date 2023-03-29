package org.coppeloons.noteshare.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainForApi(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher("/api/**")
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/notes").authenticated()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChainForWeb(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf()
                .ignoringRequestMatchers("/users/**")
                .ignoringRequestMatchers("/notes")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/style/**").permitAll()
                .requestMatchers("/script/**").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/signUp").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/login").permitAll()
                .anyRequest().hasAuthority(Role.USER.getAuthority())
                .and()
                .formLogin()
                .loginPage("/users/login")
                .defaultSuccessUrl("/welcome", true)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
