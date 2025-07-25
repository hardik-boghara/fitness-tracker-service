package com.fitnesstracker.service;

import java.util.List;

import com.fitnesstracker.model.UserDTO;

public interface UserService {
	UserDTO createUser(UserDTO userDTO);

	List<UserDTO> getAllUsers();

	UserDTO getUserById(Long id);

	UserDTO updateUser(Long id, UserDTO userDTO);

	String deleteUser(Long id);
}
