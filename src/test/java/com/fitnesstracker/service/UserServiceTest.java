package com.fitnesstracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fitnesstracker.domain.User;
import com.fitnesstracker.exception.ResourceNotFoundException;
import com.fitnesstracker.model.UserDTO;
import com.fitnesstracker.repo.UserRepository;
import com.fitnesstracker.service.impl.UserServiceImpl;

class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@InjectMocks
	private UserServiceImpl userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateUser() {
		UserDTO inputDto = new UserDTO("hardik", "123", "hardik@mail.com", "USER");
		User savedUser = User.builder().id(1L).username("hardik").email("hardik@mail.com").password("encodedPwd")
				.role("USER").build();

		when(passwordEncoder.encode("123")).thenReturn("encodedPwd");
		when(userRepository.save(any(User.class))).thenReturn(savedUser);

		UserDTO result = userService.createUser(inputDto);

		assertNotNull(result);
		assertEquals("hardik", result.getUsername());
		verify(passwordEncoder).encode("123");
		verify(userRepository).save(any(User.class));
	}

	@Test
	void testGetUserById() {
		User user = User.builder().id(1L).username("ravi").email("ravi@mail.com").role("USER").build();

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		UserDTO result = userService.getUserById(1L);

		assertNotNull(result);
		assertEquals("ravi", result.getUsername());
	}

	@Test
	void testGetUserById_NotFound() {
		when(userRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(2L));
	}

	@Test
	void testUpdateUser() {
		Long userId = 1L;
		User existingUser = User.builder().id(userId).username("hardik").email("hardik@mail.com").password("123")
				.role("USER").build();

		UserDTO updateDto = new UserDTO("tejas", "456", "tejas@mail.com", "ADMIN");

		when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
		when(passwordEncoder.encode("456")).thenReturn("encodedNewPass");
		when(userRepository.save(any(User.class))).thenReturn(existingUser);

		UserDTO result = userService.updateUser(userId, updateDto);

		assertEquals("tejas", result.getUsername());
		verify(passwordEncoder).encode("456");
		verify(userRepository).save(existingUser);
	}

	@Test
	void testDeleteUser() {
		User user = User.builder().id(1L).username("tejas").build();
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		String message = userService.deleteUser(1L);
		assertEquals("User successfully deleted", message);
		verify(userRepository).delete(user);
	}

	@Test
	void testGetAllUsers() {
		List<User> users = List.of(User.builder().id(1L).username("user1").build(),
				User.builder().id(2L).username("user2").build());

		when(userRepository.findAll()).thenReturn(users);

		List<UserDTO> result = userService.getAllUsers();
		assertEquals(2, result.size());
		assertEquals("user1", result.get(0).getUsername());
		assertEquals("user2", result.get(1).getUsername());
	}

	@Test
	void testGetAllUsers_EmptyList() {
		when(userRepository.findAll()).thenReturn(Collections.emptyList());

		assertThrows(ResourceNotFoundException.class, () -> userService.getAllUsers());
	}
}
