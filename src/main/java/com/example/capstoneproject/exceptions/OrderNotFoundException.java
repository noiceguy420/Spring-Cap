package com.example.capstoneproject.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(){
        super("Order Not Found");
    }
}
