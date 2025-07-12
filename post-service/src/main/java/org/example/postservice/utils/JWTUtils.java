package org.example.postservice.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secret;

    public boolean validateToken(String token) {
        try{
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        }
        catch(Exception e){
            log.warn("Error with validating token: {}", e.getMessage());
        }
        return false;
    }

    public Integer extractId(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Integer.class);
    }

    public List<String> extractRoles(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("roles", List.class);
    }
}
