package com.example.capstoneproject.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddUserReq {
    @Email
    @NotNull
    private String Email;
    @Min(2)
    @NotNull
    private String password;
}
