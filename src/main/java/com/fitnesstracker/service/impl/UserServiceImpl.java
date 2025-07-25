package com.fitnesstracker.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitnesstracker.domain.User;
import com.fitnesstracker.exception.ResourceNotFoundException;
import com.fitnesstracker.model.UserDTO;
import com.fitnesstracker.repo.UserRepository;
import com.fitnesstracker.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public UserDTO createUser(UserDTO userProxy) {
		ModelMapper mapper = new ModelMapper();
		User user = mapper.map(userProxy, User.class);
		user.setPassword(passwordEncoder.encode(userProxy.getPassword()));
		user.setCreatedDate(LocalDateTime.now());
		
		userRepository.save(user);

		return userProxy;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> userList = userRepository.findAll();

		if (userList == null || userList.isEmpty()) {
			throw new ResourceNotFoundException("User data is Not available");
		}

		List<UserDTO> userProxyList = new ArrayList<>(userList.size());
		ModelMapper mapper = new ModelMapper();

		userList.forEach(user -> {
			UserDTO userProxy = mapper.map(user, UserDTO.class);
			userProxyList.add(userProxy);
		});

		return userProxyList;
	}
	
	@Override
	public UserDTO getUserById(Long id) {
		User user = getUserEntityById(id);
		ModelMapper mapper = new ModelMapper();
		UserDTO userProxy = mapper.map(user, UserDTO.class);
		return userProxy;
	}

	private User getUserEntityById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public UserDTO updateUser(Long id, UserDTO userProxy) {
		User user = getUserEntityById(id);
		
		user.setUsername(userProxy.getUsername());
		user.setEmail(userProxy.getEmail());
		user.setPassword(passwordEncoder.encode(userProxy.getPassword()));
		user.setRole(userProxy.getRole());
		user.setModifiedDate(LocalDateTime.now());
		userRepository.save(user);
		return userProxy;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String deleteUser(Long id) {
		User user = getUserEntityById(id);
		userRepository.delete(user);
		return "User successfully deleted";
	}
}
