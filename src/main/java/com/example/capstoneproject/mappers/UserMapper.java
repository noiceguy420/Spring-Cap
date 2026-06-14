package com.example.capstoneproject.mappers;

import com.example.capstoneproject.dtos.AddUserReq;
import com.example.capstoneproject.dtos.UserDto;
import com.example.capstoneproject.entities.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserDto userDto);
    UserDto toDto(User user);
    User reqToUser(AddUserReq req);
}