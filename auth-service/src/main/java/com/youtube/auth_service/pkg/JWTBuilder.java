package com.youtube.auth_service.pkg;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.youtube.auth_service.internal.domain.entities.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTBuilder {
    
    @Value("${jwt.access.secret}")
    private String accessToken;

    @Value("${jwt.refresh.secret1}")
    private String refreshToken;

    public Map<String, String> generateTokens(UserEntity userEntity){
        Map<String, String> tokens = new HashMap<>();

        tokens.put("accessToken", createToken(userEntity, accessToken, 15 * 60 * 1000));
        tokens.put("refreshToken", createToken(userEntity, refreshToken, 7 * 24 * 60 * 60 * 1000));

        return tokens;

    }

    private String createToken(UserEntity userEntity, String secret, long expirationTime){
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
            .subject(userEntity.getEmail())
            .claim("id", userEntity.getId().toString())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(key)
            .compact();
    }
}
