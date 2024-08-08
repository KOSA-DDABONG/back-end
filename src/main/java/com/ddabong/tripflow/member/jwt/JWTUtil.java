package com.ddabong.tripflow.member.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    private SecretKey secretKey;
    
    // 키 생성
    public JWTUtil(@Value("${spring.jwt.secret}")String secret){
        this.secretKey = new SecretKeySpec(secret
                .getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key()
                        .build()
                        .getAlgorithm());
    }

    // 토큰의 username 검증
    public String getUsername(String token){
        return Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(token)
                .getPayload().get("userId", String.class);
    }

    // 토큰의 role 검증
    public String getRole(String token){
        return Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(token)
                .getPayload().get("role", String.class);
    }

    // 토큰의 만료시간 검증
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(token).getPayload()
                .getExpiration().before(new Date());
    }

    // 토큰생성
    public String createJwt(String username, String role, Long expiredMs){
        return Jwts.builder()
                .claim("userId", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 현재 발행시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 발행시간 + 유효시간 = 만료시간
                .signWith(secretKey)
                .compact();
    }
}
