package com.example.capstoneproject.services;

import com.example.capstoneproject.entities.User;
import com.example.capstoneproject.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    public User getCurrentUser(){
        var uid = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findById(uid).orElse(null);
        return user;
    }
}
