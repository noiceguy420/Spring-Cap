package com.example.capstoneproject.services;

import com.example.capstoneproject.config.JwtConfig;
import com.example.capstoneproject.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;


@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;


    public Jwt generateAccessToken(User user){
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }
    public Jwt generateRefreshToken(User user){
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }
    public Jwt parse(String token){
        try {
            var claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey()).build()
                .parseSignedClaims(token).getPayload();
    }

    private Jwt generateToken(User user, long tokenExpiration) {

        System.out.println("current system time: " + System.currentTimeMillis());
        long newTime = tokenExpiration + System.currentTimeMillis();
        System.out.println("exp date: " + new Date(newTime));
        /*String token = Jwts.builder().subject(user.getId().toString())
                .claim("Email", user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(newTime))
                .signWith(jwtConfig.getSecretKey()).compact();
        return new Jwt(token, jwtConfig.getSecretKey());*/ //valid but another is used
        var claims = Jwts.claims().subject(user.getId().toString())
                .add("Email", user.getEmail())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(newTime)).build();
        return new Jwt(claims, jwtConfig.getSecretKey());
    }
}
