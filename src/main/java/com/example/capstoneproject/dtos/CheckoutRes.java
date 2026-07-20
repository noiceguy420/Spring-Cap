package com.example.capstoneproject.dtos;

import lombok.Data;

@Data
public class CheckoutRes {
    private Long id;

    public CheckoutRes(Long id){
        this.id = id;
    }
}
