package com.example.capstoneproject.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckoutReq {
    @NotNull(message = "cartId is required")
    private UUID cartId;
}
