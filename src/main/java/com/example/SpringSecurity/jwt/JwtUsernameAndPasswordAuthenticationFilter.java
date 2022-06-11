package com.example.SpringSecurity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUsernameAndPasswordAuthenticationFilter extends
        UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;



    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
                                                throws AuthenticationException {
        try {


            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
                                            throws IOException, ServletException {
        String key = "SecureSecureSecureSecureSecureSecureSecureSecureSecureSecureSecureSecureSecure";
//        String token = Jwts.builder()
//                .setSubject(authResult.getName())
//                .claim("authorities", authResult.getAuthorities())
//                .setIssuedAt(new Date())
//                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
//                .signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
//                .compact();
        String access_token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
//                .setExpiration(new java.sql.Date(System.currentTimeMillis()+30*60*1000))
                .setIssuer(request.getRequestURL().toString())
                .signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                .compact();
        String refresh_token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
//                .setExpiration(new java.sql.Date(System.currentTimeMillis()+30*60*1000))
                .setIssuer(request.getRequestURL().toString())
                .signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                .compact();

        response.setHeader("access_token",access_token);
        response.setHeader("refresh_token",refresh_token);
        Map<String,String> tokens = new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),tokens);
//        response.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
//        response.addHeader("Authorization","Bearer"+token);

//        response.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
//        response.addHeader("Authorization","Bearer"+access_token);
//        response.addHeader("Authorization","Bearer"+refresh_token);
    }


}
