package com.fitnesstracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnesstracker.model.ApiResponse;
import com.fitnesstracker.model.UserDTO;
import com.fitnesstracker.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<ApiResponse<UserDTO>> createUser(@Valid @RequestBody UserDTO dto) {
		return ResponseEntity.ok(new ApiResponse<UserDTO>(true, "New User Created", userService.createUser(dto)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
		return ResponseEntity.ok(new ApiResponse<List<UserDTO>>(true, "Successfully get Users Detail", userService.getAllUsers()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<UserDTO>> deleteUser(@PathVariable Long id) {
		String message = userService.deleteUser(id);
		return ResponseEntity.ok(new ApiResponse<>(true, message));
	}
}
