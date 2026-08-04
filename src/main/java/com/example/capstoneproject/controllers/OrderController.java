package com.example.capstoneproject.controllers;

import com.example.capstoneproject.dtos.ErrorDto;
import com.example.capstoneproject.dtos.OrderDto;
import com.example.capstoneproject.entities.Order;
import com.example.capstoneproject.entities.User;
import com.example.capstoneproject.exceptions.OrderNotFoundException;
import com.example.capstoneproject.exceptions.UnAuthorizedOrderException;
import com.example.capstoneproject.mappers.OrderMapper;
import com.example.capstoneproject.repositories.OrderRepository;
import com.example.capstoneproject.services.AuthService;
import com.example.capstoneproject.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/my orders")
    public ResponseEntity<List<OrderDto>> getAllMyOrders(){
        return ResponseEntity.ok(orderService.getAllMyOrders());
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(Exception ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(ex.getMessage()));
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDto> handleOrderNotFoundException(){
        return ResponseEntity.notFound().build();
    }
}
