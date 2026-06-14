package com.example.capstoneproject.controllers;

import com.example.capstoneproject.dtos.AddUserReq;
import com.example.capstoneproject.dtos.UserDto;
import com.example.capstoneproject.entities.User;
import com.example.capstoneproject.mappers.UserMapper;
import com.example.capstoneproject.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public Iterable<UserDto> getAllusers(){
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/new User")
    public ResponseEntity<?> RegisterUser(@Valid @RequestBody AddUserReq req, UriComponentsBuilder uriBuilder){
        if(userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("email", "email is already registered"));
        }
        User user = userMapper.reqToUser(req);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        userRepository.save(user);

        URI uri = uriBuilder.path("users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
