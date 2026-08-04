package com.example.capstoneproject.exceptions;

public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException(){
        super("Cart Not Found");
    }
}
