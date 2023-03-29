package org.coppeloons.noteshare.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{

    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .ignoringRequestMatchers("/api/users")
                .ignoringRequestMatchers("/users/**")
                .ignoringRequestMatchers("/api/notes")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/style/**").permitAll()
                .requestMatchers("/script/**").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/signUp").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/login").permitAll()
                .requestMatchers("/viewNotes").hasAnyAuthority(Role.ADMIN.getAuthority())
                .anyRequest().hasAnyAuthority(Role.USER.getAuthority())
                .and()
                .formLogin()
                .loginPage("/users/login")
                .defaultSuccessUrl("/welcome", true);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
