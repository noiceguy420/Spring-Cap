package com.example.capstoneproject.services;

import com.example.capstoneproject.dtos.CheckoutReq;
import com.example.capstoneproject.dtos.CheckoutRes;
import com.example.capstoneproject.dtos.ErrorDto;
import com.example.capstoneproject.entities.Order;
import com.example.capstoneproject.exceptions.CartEmptyException;
import com.example.capstoneproject.exceptions.CartNotFoundException;
import com.example.capstoneproject.repositories.CartRepository;
import com.example.capstoneproject.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public CheckoutRes checkout(CheckoutReq req){
        var cart = cartRepository.getCartWithItems(req.getCartId()).orElse(null);
        if(cart == null)
            //return ResponseEntity.badRequest().body(new ErrorDto("cart not found"));
            throw new CartNotFoundException();
        if(cart.isEmpty())
            //return ResponseEntity.badRequest().body(new ErrorDto("cart is empty"));
            throw new CartEmptyException();
        Order order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return new CheckoutRes(order.getId());
    }
}
