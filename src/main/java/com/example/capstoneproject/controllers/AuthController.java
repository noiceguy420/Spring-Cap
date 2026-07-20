package com.example.capstoneproject.controllers;

import com.example.capstoneproject.config.JwtConfig;
import com.example.capstoneproject.dtos.UserDto;
import com.example.capstoneproject.entities.User;
import com.example.capstoneproject.mappers.UserMapper;
import com.example.capstoneproject.repositories.UserRepository;
import com.example.capstoneproject.services.AuthService;
import com.example.capstoneproject.services.Jwt;
import com.example.capstoneproject.services.JwtService;
import com.example.capstoneproject.dtos.JwtResponse;
import com.example.capstoneproject.dtos.UserLoginReq;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtConfig jwtConfig;
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid UserLoginReq req, HttpServletResponse response){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                req.getEmail(), req.getPassword()));
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow();
        Jwt accessToken = jwtService.generateAccessToken(user);
        Jwt refreshToken = jwtService.generateRefreshToken(user);

        var cookie = new Cookie("refreshToken", refreshToken.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value = "refreshToken") String refreshToken){
        Jwt refreshJwt = jwtService.parse(refreshToken);
        if(refreshJwt == null || refreshJwt.isExpired())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        User user = userRepository.findById(refreshJwt.getUserId()).orElseThrow();
        Jwt accessJwt = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(new JwtResponse(accessJwt.toString()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        User user = authService.getCurrentUser();
        if(user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
