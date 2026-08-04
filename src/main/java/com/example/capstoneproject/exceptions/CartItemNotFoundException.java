package com.example.capstoneproject.exceptions;

public class CartItemNotFoundException extends RuntimeException{
    public CartItemNotFoundException(){
        super("Cart Item Not Found");
    }
}
