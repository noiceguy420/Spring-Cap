package com.example.capstoneproject.exceptions;

public class UnAuthorizedOrderException extends RuntimeException {
    public UnAuthorizedOrderException(){
        super("you are not Authorized to access this order");
    }
}
