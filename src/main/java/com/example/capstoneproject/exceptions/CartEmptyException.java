package com.example.capstoneproject.exceptions;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException(){
        super("cart is empty");
    }

}
