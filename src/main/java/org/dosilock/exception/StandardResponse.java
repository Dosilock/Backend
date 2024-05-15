package org.dosilock.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StandardResponse<T> {
	private int status;
	private T payload;
}
