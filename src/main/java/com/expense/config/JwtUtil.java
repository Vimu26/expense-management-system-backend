package com.expense.config;

import com.expense.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef"; // 64 chars = 512 bits
    private final long EXPIRATION_TIME = 86400000; // 1 day in ms

    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())  // custom claim: user ID can add more claims if needed
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS512)  // use SecretKey + algorithm
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)    // use SecretKey
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public Long extractUserId(String token) {
        return getClaims(token).get("id", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)  // use SecretKey
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            // all JWT exceptions are subclasses of JwtException
            return false;
        }
    }
}
