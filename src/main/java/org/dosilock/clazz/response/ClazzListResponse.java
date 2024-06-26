package org.dosilock.clazz.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClazzListResponse {

	@Schema(description = "반 고유링크")
	private String clazzLink;
	
	@Schema(description = "반 이름", example = "반 이름")
	private String clazzName;

	@Schema(description = "방장 여부")
	@JsonProperty("isOwned")
	private boolean isOwned;

	@Schema(description = "멤버 수", example = "멤버 수")
	private int memberCount;

	@Schema(description = "반 아이콘", example = "base64 image")
	private String clazzIcon;
}
