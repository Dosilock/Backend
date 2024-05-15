package org.dosilock.exception;

import lombok.Data;

@Data
public class ErrorResponseDto {

	private String errorCode;
	private String errorMessage;

	public ErrorResponseDto(ErrorMessage errorMessage) {
		this.errorCode = errorMessage.getCode();
		this.errorMessage = errorMessage.getMessage();
	}
}
