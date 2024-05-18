package org.dosilock.clazz.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClazzInfoResponse {

	@Schema(description = "반 이름", example = "반 이름")
	private String clazzName;

	@Schema(description = "반 아이콘", example = "base64 image")
	private String clazzIcon;

	@Schema(description = "멤버 수")
	private int memberCount;
}
