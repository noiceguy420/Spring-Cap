package com.example.capstoneproject.dtos;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Data
public class CartProductDto{
    Integer id;
    String name;
    Float price;
}