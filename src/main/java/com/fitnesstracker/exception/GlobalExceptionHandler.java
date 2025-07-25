package com.fitnesstracker.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fitnesstracker.model.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handle custom resource not found error
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException ex) {
		log.error("Exception --> {}", ex);
		return new ResponseEntity<>(new ApiResponse<>(false, ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	// Handle validation errors (DTOs)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<String>> handleValidationErrors(MethodArgumentNotValidException ex) {
		try {
			String message = ex.getBindingResult().getFieldErrors().stream().map(err -> err.getDefaultMessage())
					.collect(Collectors.joining(", "));

			return new ResponseEntity<>(new ApiResponse<>(false, message.toString()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse<>(false, "Something goes wrong, Please contact support"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Handle generic errors
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
		log.error("Exception --> {}", ex);
		return new ResponseEntity<>(new ApiResponse<>(false, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
