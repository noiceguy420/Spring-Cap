package com.example.capstoneproject.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddItemRequest {
    @NotNull
    private Long productId;
}
