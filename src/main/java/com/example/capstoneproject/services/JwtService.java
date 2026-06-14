package com.example.capstoneproject.services;

import com.example.capstoneproject.config.JwtConfig;
import com.example.capstoneproject.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;


@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;


    public String generateAccessToken(User user){
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }
    public String generateRefreshToken(User user){
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private String generateToken(User user, long tokenExpiration) {

        System.out.println("current system time: " + System.currentTimeMillis());
        long newTime = tokenExpiration + System.currentTimeMillis();
        System.out.println("token exp: " + newTime);
        return Jwts.builder().subject(user.getId().toString())
                .claim("Email", user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(newTime))
                .signWith(jwtConfig.getSecretKey()).compact();
    }

    public boolean validateToken(String token){
        try{
            var claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        }
        catch (JwtException e) {return false;}
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey()).build()
                .parseSignedClaims(token).getPayload();
    }

    public Integer getIdFromToken(String token){
        return Integer.parseInt(getClaims(token).getSubject());

    }
}
