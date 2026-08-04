package com.example.capstoneproject.services;

import com.example.capstoneproject.dtos.OrderDto;
import com.example.capstoneproject.entities.Order;
import com.example.capstoneproject.entities.Role;
import com.example.capstoneproject.entities.User;
import com.example.capstoneproject.exceptions.OrderNotFoundException;
import com.example.capstoneproject.exceptions.UnAuthorizedOrderException;
import com.example.capstoneproject.mappers.OrderMapper;
import com.example.capstoneproject.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllMyOrders() {
        User usr = authService.getCurrentUser();
        List<Order> orders = orderRepository.getOrdersByCustomer(usr);
        return orders.stream().map(orderMapper::toDto).toList();
    }
    public OrderDto getOrder(@PathVariable Long id){
        User usr = authService.getCurrentUser();
        Order order = orderRepository.getOrderWithItems(id).orElseThrow(OrderNotFoundException::new);
        if(!order.isPlacedBy(usr) && !usr.isAdmin())
            throw new AccessDeniedException("you don't have access to this order");
        return orderMapper.toDto(order);
    }
}
