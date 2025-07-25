package com.fitnesstracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnesstracker.model.ApiResponse;
import com.fitnesstracker.model.UserDTO;
import com.fitnesstracker.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable Long id) {
		return ResponseEntity.ok(new ApiResponse<UserDTO>(true, "Successfully get User Detail", userService.getUserById(id)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDto) {
		return ResponseEntity.ok(new ApiResponse<UserDTO>(true, "User Updated", userService.updateUser(id, userDto)));
	}
}
