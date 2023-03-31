package org.coppeloons.noteshare.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.coppeloons.noteshare.security.Role.ADMIN;

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
                .requestMatchers(HttpMethod.DELETE, "/api/notes/*").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/api/notes/*").authenticated()
                .anyRequest().hasAuthority(ADMIN.getAuthority())
                .and()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChainForWeb(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests()
                .requestMatchers("/style/**").permitAll()
                .requestMatchers("/script/**").permitAll()
                .requestMatchers("/error/**").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/signUp").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/welcome").authenticated()
                .requestMatchers(HttpMethod.GET, "/newNote").authenticated()
                .requestMatchers(HttpMethod.GET, "/*/viewNotes").authenticated()
                .requestMatchers(HttpMethod.GET, "/viewUsers").authenticated()
                .requestMatchers(HttpMethod.GET, "/viewNotes/*").authenticated()
                .requestMatchers(HttpMethod.GET, "/logout").permitAll()
                .anyRequest().hasAuthority(ADMIN.getAuthority())
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
