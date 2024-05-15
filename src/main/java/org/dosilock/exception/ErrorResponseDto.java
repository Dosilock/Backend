package org.dosilock.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {
	private int errorCode;
	private String errorMessage;
}
