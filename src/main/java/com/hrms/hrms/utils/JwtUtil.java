package com.hrms.hrms.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private static final String SECRET_KEY =
            "jaydeepisagooddeveloperandsecurekey12345";
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String userDetails) {
        HashMap<String,Object> claims= new HashMap<>();
        return  createToken(claims,userDetails);
    }

    private String createToken(HashMap<String,Object> claims,String userDetails) {
            return Jwts.builder()
                    .claims(claims)
                    .subject(userDetails)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+1000*60*1))
                    .signWith(getSignInKey())
                    .compact();
    }


    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

}
