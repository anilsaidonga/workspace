package com.example.userservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/v1/users/")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto requestUserDto) {
        UserDto responseUserDto = userService.createUser(requestUserDto);
        return ResponseEntity.status(201).body(responseUserDto);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allUsers = userService.getAllUsers();
        if (allUsers.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/get-user-by-id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<UserDto> responseUserDtoOptional = userService.getUserById(id);

        if (responseUserDtoOptional.isPresent()) {
            return ResponseEntity.ok(responseUserDtoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("delete-user-by-id/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }

}
