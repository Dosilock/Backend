package org.dosilock.member.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestMemberEmailDto {
	@NotNull(message = "이메일을 작성해주세요.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;
}
