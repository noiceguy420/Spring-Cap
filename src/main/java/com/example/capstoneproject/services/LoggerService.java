package com.example.capstoneproject.services;

import org.springframework.stereotype.Service;

@Service
public class LoggerService {
    public  void log(String message){
        System.out.println(message); //TODO: change to a real logger when applicable
    }
}
