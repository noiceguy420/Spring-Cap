package com.example.capstoneproject.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.capstoneproject.entities.User}
 */
@Value
public class UserDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 100)
    String email;
}