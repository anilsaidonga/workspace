package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/api/users")
	public ResponseEntity<List<UserDto>> getAllUsers()
	{
		List<UserDto> response = userService.getAllUsers();
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/api/users")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto requestUserDto)
	{
		UserDto responseUserDto = userService.createUser(requestUserDto);
		return ResponseEntity.ok(responseUserDto);
	}
}
