package org.dosilock.exception;

import io.swagger.v3.oas.annotations.media.Schema;

public class Swagger500StandardResponseDto<T> extends StandardResponseDto {

	@Schema(description = "응답 상태", example = "500")
	private int status;

	@Schema(description = "응답 내용", example = "{"
		+ "\"errorCode\":\"000\","
		+ "\"errorMessage\":\"에러 메세지\""
		+ "}")
	private T payload;

	Swagger500StandardResponseDto(int status, Object payload) {
		super(status, payload);
	}
}
