package com.ithuhu.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;


public class JwtUtils {
    //密钥
    private static final String SECRET_KEY = "Z29vZGl1YW9wZW5zb2Z0d2FyZXNlY3JldGtleTEyMzQ1Njc4OQ==";
    //有效期
    private static final long EXPIRATION_TIME = 864_000_000;

    //获取加密密钥
    public static SecretKey getSecretKey() {
        byte[] secretKeyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }

    //生成JWT令牌
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))//过期时间
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)//签名密钥，使用HMAC-SHA算法
                .compact();
    }

    //解析JWT令牌
    public static Claims parseToken(String token) {
        SecretKey key = getSecretKey();
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
