package org.dosilock.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.Entity;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(IllegalStateException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handle(IllegalStateException e) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<Object> handle(UsernameNotFoundException e) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handle(IllegalArgumentException e) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
