package com.example.capstoneproject.mappers;

import com.example.capstoneproject.dtos.OrderDto;
import com.example.capstoneproject.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
