package org.dosilock.member.request;

import java.time.LocalDateTime;

import org.dosilock.clazz.entity.Clazz;
import org.dosilock.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class RequestClazzBlackList {
	private Long id;
	private Clazz clazzId;
	private Member memberId;
	private LocalDateTime createdAt;
}
