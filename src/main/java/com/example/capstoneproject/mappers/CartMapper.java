package com.example.capstoneproject.mappers;

import com.example.capstoneproject.dtos.CartDto;
import com.example.capstoneproject.dtos.CartItemDto;
import com.example.capstoneproject.entities.Cart;
import com.example.capstoneproject.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);
    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
