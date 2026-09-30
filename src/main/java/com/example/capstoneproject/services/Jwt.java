package com.example.capstoneproject.services;

import com.example.capstoneproject.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;

public class Jwt {
    private final Claims claims;
    private final SecretKey key;
    private final LoggerService logger;

    public Jwt(Claims claims, SecretKey key){
        this.claims = claims;
        this.key = key;
        logger = new LoggerService();
        logger.log("key = " + key);
    }

    public boolean isExpired(){
        return claims.getExpiration().before(new Date());
    }

    public Integer getUserId(){
        return Integer.parseInt(claims.getSubject());

    }

    public Role getRole(){
        return Role.valueOf(claims.get("role", String.class));
    }

    @Override
    public String toString(){
        return Jwts.builder().claims(claims).signWith(key).compact();
    }
}
