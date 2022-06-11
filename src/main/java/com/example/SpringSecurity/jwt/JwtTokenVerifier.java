package com.example.SpringSecurity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.sun.mail.util.MimeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class JwtTokenVerifier extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {
        if (request.getServletPath().equals("api/v1/registration/login") || request.getServletPath().equals("api/v1/registration/refresh")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader("Authorization");
            if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = authorizationHeader.replace("Bearer ", "");

            try {

                String secreteKey = "SecureSecureSecureSecureSecureSecureSecureSecureSecureSecureSecureSecureSecure";
                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(secreteKey.getBytes(StandardCharsets.UTF_8)))
                        .build()
                        .parseClaimsJws(token);
                Claims body = claimsJws.getBody();
                String username = body.getSubject();
                var authorities = (List<Map<String, String>>) body.get("authorities");

                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                        .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                        .collect(Collectors.toSet());
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        simpleGrantedAuthorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                response.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
//                filterChain.doFilter(request, response);
            } catch (JwtException e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);

//            throw new IllegalStateException(String.format("Token %s cannot be trusted",token));

            }

            filterChain.doFilter(request, response);
        }

        }


}
