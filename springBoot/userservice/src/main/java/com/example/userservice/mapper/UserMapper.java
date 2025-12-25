package com.example.userservice.mapper;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserDto userDto)
    {
        return new UserEntity(userDto.getName(), userDto.getEmail());
    }

    public static UserDto toDto(UserEntity userEntity)
    {
        return new UserDto(userEntity.getName(), userEntity.getEmail());
    }
    
}
