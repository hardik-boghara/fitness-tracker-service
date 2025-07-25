package com.fitnesstracker.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = Include.NON_NULL)
public class ApiResponse<T> {

	private String timestamp;
	private boolean success;
	private String message;
	private T data;

	public ApiResponse() {
		this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
	}

	public ApiResponse(boolean success, String message, T data) {
		this();
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public ApiResponse(boolean success, String message) {
		this();
		this.success = success;
		this.message = message;
	}
}
