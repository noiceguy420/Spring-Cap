package com.example.capstoneproject.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
public class CartDto {
    private UUID id;
    private List<CartItemDto> cartItems = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
