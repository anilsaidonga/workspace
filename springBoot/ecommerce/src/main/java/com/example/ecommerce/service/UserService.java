package com.example.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.entity.UserEntity;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	public final UserRepository userRepository;
	
	public List<UserDto> getAllUsers()
	{
		return userRepository.findAll().stream()
				.map(UserMapper::toDto).toList();
	}
	
	public UserDto createUser(UserDto requestUserDto)
	{
		UserEntity requestUserEntity = userRepository.save(UserMapper.toEntity(requestUserDto));
		return UserMapper.toDto(requestUserEntity);
	}
}
