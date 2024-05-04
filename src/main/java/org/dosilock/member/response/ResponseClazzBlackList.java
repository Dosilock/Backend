package org.dosilock.member.response;

import java.time.LocalDateTime;

import org.dosilock.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ResponseClazzBlackList {
	private Long id;
	// FK Entity 추가 시 적용 필요
	private Long clazzId;
	private Member memberId;
	private LocalDateTime createdAt;
}
