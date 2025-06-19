package com.cinema.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long expirationMillis;
    
    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getEncoder().encode(jwtSecret.getBytes()); 
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    
    // Genera il token JWT.
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key)
                .compact();
    }
    
    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
    
    public String getUsername(String token) {
        return validateToken(token).getBody().getSubject();
    }
    
    public String getRole(String token) {
        return (String) validateToken(token).getBody().get("role");
    }
}

