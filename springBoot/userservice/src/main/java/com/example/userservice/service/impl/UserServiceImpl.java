package com.example.userservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    
    @Override
    public UserDto createUser(UserDto requestUserDto) {
        UserEntity requestUserEntity = UserMapper.toEntity(requestUserDto);
        UserEntity responseUserEntity = userRepository.save(requestUserEntity);
        UserDto responseUserDto = UserMapper.toDto(responseUserEntity);
        return responseUserDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> allUsersEntities = userRepository.findAll();
        List<UserDto> allUserDtos = new ArrayList<>();
        for (UserEntity i : allUsersEntities)
            allUserDtos.add(UserMapper.toDto(i));
        return allUserDtos;
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        Optional<UserEntity> responseUserEntityOptional = userRepository.findById(id);

        if (responseUserEntityOptional.isPresent())
        {
            UserDto responseUserDto = UserMapper.toDto(responseUserEntityOptional.get());
            return Optional.of(responseUserDto);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id))
        {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
