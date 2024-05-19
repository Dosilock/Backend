package org.dosilock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
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
	public ResponseEntity<StandardResponseDto> handle(UsernameNotFoundException e) {
		return new ResponseEntity<>(StandardResponseDto
			.<String>builder()
			.status(401)
			.payload(e.getMessage())
			.build(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardResponseDto> handle(IllegalArgumentException e) {
		return new ResponseEntity<>(StandardResponseDto
			.<String>builder()
			.status(500)
			.payload(e.getMessage())
			.build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<StandardResponseDto> handle(AccessDeniedException e) {
		return new ResponseEntity<>(StandardResponseDto
			.<String>builder()
			.status(403)
			.payload("접근 권한이 없습니다.")
			.build(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<StandardResponseDto> handle(AuthenticationException e) {
		return new ResponseEntity<>(StandardResponseDto
			.<String>builder()
			.status(401)
			.payload("로그인에 실패 하였습니다.")
			.build(), HttpStatus.UNAUTHORIZED);
	}

}
