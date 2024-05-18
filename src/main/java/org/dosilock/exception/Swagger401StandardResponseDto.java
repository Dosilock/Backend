package org.dosilock.exception;

import io.swagger.v3.oas.annotations.media.Schema;

public class Swagger401StandardResponseDto<T> extends StandardResponseDto {

	@Schema(description = "응답 상태", example = "401")
	private int status;

	@Schema(description = "응답 내용", example = "Unauthorized")
	private T payload;

	Swagger401StandardResponseDto(int status, Object payload) {
		super(status, payload);
	}
}
