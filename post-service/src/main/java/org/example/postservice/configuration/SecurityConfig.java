package org.example.postservice.configuration;

import lombok.RequiredArgsConstructor;
import org.example.postservice.filters.JWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.addAllowedOrigin("*");
                    corsConfiguration.addAllowedHeader("*");
                    corsConfiguration.addAllowedMethod("*");
                    return corsConfiguration;
                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests
                            .requestMatchers("/api/posts/**").authenticated()
                            .anyRequest().permitAll();
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
