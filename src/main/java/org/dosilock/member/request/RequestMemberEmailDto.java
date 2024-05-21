package org.dosilock.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "이메일 DTO")
public class RequestMemberEmailDto {

	@Schema(description = "이메일", example = "example@gmail.com", required = true)
	@NotNull(message = "이메일을 작성해주세요.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;
}
