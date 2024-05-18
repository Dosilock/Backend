package org.dosilock.exception;

import io.swagger.v3.oas.annotations.media.Schema;

public class Swagger200StandardResponseDto<T> extends StandardResponseDto {

	@Schema(description = "응답 상태", example = "200")
	private int status;

	@Schema(description = "응답 내용", example = "Success")
	private T payload;

	Swagger200StandardResponseDto(int status, Object payload) {
		super(status, payload);
	}
}
