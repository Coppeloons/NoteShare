package org.coppeloons.noteshare.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{

    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .ignoringRequestMatchers("/users/**")
                .ignoringRequestMatchers("/notes")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/style.css").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/signUp").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/signUp").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/login").permitAll()
                .anyRequest().authenticated()
                .and()
/*
                .formLogin()
*/
                .formLogin()
                .loginPage("/users/login")
                .defaultSuccessUrl("/welcome", true)
        /*.loginPage("/login")*/;

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
