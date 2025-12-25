package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.entity.UserEntity;

public class UserMapper {
	
	public static UserEntity toEntity(UserDto userDto)
	{
		return new UserEntity(userDto.getId(), userDto.getFirstName(), userDto.getLastName());
	}
	
	public static UserDto toDto(UserEntity userEntity)
	{
		return new UserDto(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName());
	}
	
}
