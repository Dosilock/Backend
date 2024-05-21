package org.dosilock.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StandardResponseDto<T> {

	@Schema(example = "200")
	private int status;

	private T payload;
}
