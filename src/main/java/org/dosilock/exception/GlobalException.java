package org.dosilock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(UserErrorException.class)
	public ResponseEntity<StandardResponseDto<ErrorResponseDto>> handle(UserErrorException e) {
		return new ResponseEntity<>(StandardResponseDto
			.<ErrorResponseDto>builder()
			.status(500)
			.payload(e.getErrorResponseDto())
			.build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<StandardResponseDto> handle(IllegalStateException e) {
		return new ResponseEntity<>(StandardResponseDto
			.<String>builder()
			.status(500)
			.payload(e.getMessage())
			.build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> handle(UsernameNotFoundException e) {
		return new ResponseEntity<>(StandardResponseDto
			.<String>builder()
			.status(401)
			.payload(e.getMessage())
			.build(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handle(IllegalArgumentException e) {
		return new ResponseEntity<>(StandardResponseDto
			.<String>builder()
			.status(500)
			.payload(e.getMessage())
			.build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
