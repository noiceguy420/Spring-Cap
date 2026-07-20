package com.example.capstoneproject.controllers;

import com.example.capstoneproject.dtos.CheckoutReq;
import com.example.capstoneproject.dtos.CheckoutRes;
import com.example.capstoneproject.entities.Order;
import com.example.capstoneproject.entities.OrderItem;
import com.example.capstoneproject.entities.OrderStatus;
import com.example.capstoneproject.repositories.CartRepository;
import com.example.capstoneproject.repositories.OrderRepository;
import com.example.capstoneproject.services.AuthService;
import com.example.capstoneproject.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/checkout")
@AllArgsConstructor
public class CheckoutController {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutReq req){
        var cart = cartRepository.getCartWithItems(req.getCartId()).orElse(null);
        if(cart == null)
            return ResponseEntity.badRequest().body(Map.of("error", "cart not found"));
        if(cart.getCartItems().isEmpty())
            return ResponseEntity.badRequest().body(Map.of("error", "cart is empty"));
        var order = new Order();
        order.setCustomer(authService.getCurrentUser());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(cart.getTotalPrice());

        cart.getCartItems().forEach(item ->{
            var orderItem = new OrderItem();
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getTotalPrice());
            orderItem.setUnitPrice(item.getProduct().getPrice());
            orderItem.setOrder(order);
            order.getItems().add(orderItem);
        });
        orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return ResponseEntity.ok(new CheckoutRes(order.getId()));
    }
}
