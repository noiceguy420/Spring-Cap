package com.example.capstoneproject.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private OrderProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice;


}
