package com.example.userservice.service;

import java.util.List;
import java.util.Optional;

import com.example.userservice.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);

    List<UserDto> getAllUsers();

    Optional<UserDto> getUserById(Long id);

    boolean deleteUser(Long id);
    
}
