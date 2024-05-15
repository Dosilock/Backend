package org.dosilock.exception;

import lombok.Getter;

public class UserErrorException extends RuntimeException {

	@Getter
	private ErrorResponseDto errorResponseDto;

	public UserErrorException(ErrorResponseDto errorResponseDto) {
		super(errorResponseDto.getErrorMessage());
		this.errorResponseDto = errorResponseDto;
	}
}
