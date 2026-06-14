package com.example.capstoneproject.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {
    @NotNull(message = "item quantity can't be empty")
    @Min(value = 1, message = "item quantity can't be less than 1")
    @Max(value = 99, message = "item quantity can't be more than 99")
    private Integer quantity;
}
