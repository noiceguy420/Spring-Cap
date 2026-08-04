package com.example.capstoneproject.controllers;

import com.example.capstoneproject.dtos.CheckoutReq;
import com.example.capstoneproject.dtos.CheckoutRes;
import com.example.capstoneproject.dtos.ErrorDto;
import com.example.capstoneproject.exceptions.CartEmptyException;
import com.example.capstoneproject.exceptions.CartNotFoundException;
import com.example.capstoneproject.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
@AllArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public CheckoutRes checkout(@Valid @RequestBody CheckoutReq req){
        return checkoutService.checkout(req);
    }

    @ExceptionHandler({CartEmptyException.class, CartNotFoundException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
