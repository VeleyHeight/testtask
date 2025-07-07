package org.example.usersservice.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.usersservice.usersDTO.JwtAuthenticationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    Integer expiration;
    @Value("${jwt.refresh-expiration}")
    Integer refreshExpiration;

    public JwtAuthenticationDTO generateAccessAndRefreshToken(UserDetails userDetails) {
        return new JwtAuthenticationDTO(generateToken(userDetails), generateRefreshToken(userDetails));
    }

    public JwtAuthenticationDTO refreshAccessAndRefreshToken(UserDetails userDetails, String token) {
        return new JwtAuthenticationDTO(generateToken(userDetails), token);
    }

    private String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .compact();
    }
    private String generateRefreshToken(UserDetails userDetails){
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+refreshExpiration))
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        }
        catch (Exception e){
            log.warn("Ошибка при валидации токена: {}", e.getMessage());
        }
        return false;
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
