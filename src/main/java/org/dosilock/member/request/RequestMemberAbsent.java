package org.dosilock.member.request;

import org.dosilock.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class RequestMemberAbsent {
	private Long id;
	private Member memberId;
	private Long periodId;
	private Boolean attendanceStatus;
}
