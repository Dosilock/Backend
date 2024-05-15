package org.dosilock.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StandardResponseDto<T> {
	@Schema(description = "응답 코드", required = true, example = "000")
	private int status;

	@Schema(description = "응답 내용", required = true, example = "성공/실패 내용")
	private T payload;
}
