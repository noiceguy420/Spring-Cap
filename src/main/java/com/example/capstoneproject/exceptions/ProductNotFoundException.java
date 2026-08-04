package com.example.capstoneproject.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(){
        super("Product Not Found");
    }
}
