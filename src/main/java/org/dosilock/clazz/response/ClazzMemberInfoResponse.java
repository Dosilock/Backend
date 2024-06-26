package org.dosilock.clazz.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClazzMemberInfoResponse {

	@Schema(description = "닉네임", example = "nickname")
	private String ninkname;

	@Schema(description = "프로필 사진", example = "https://example.com/image.png")
	private String profileImg;
}
