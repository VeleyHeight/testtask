package org.example.postservice.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postservice.utils.JWTUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (token != null && jwtUtils.validateToken(token)) {
            setUserDetailsServiceToSecurityContextHolder(token);
        }
        filterChain.doFilter(request, response);
    }

    private void setUserDetailsServiceToSecurityContextHolder(String token){
        Integer id = jwtUtils.extractId(token);
        List<String> roles = jwtUtils.extractRoles(token);
        List<? extends GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(id.toString(),
                null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
            return bearerToken;
        }
        return null;
    }
}
