package com.fitnesstracker.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1485053844265386313L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
