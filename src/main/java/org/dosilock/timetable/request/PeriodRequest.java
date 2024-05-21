package org.dosilock.timetable.request;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class PeriodRequest {

	@Schema(description = "교시 이름", example = "교시 이름")
	private String periodName;

	@Schema(description = "교시 시작 시간", example = "교시 시작 시간")
	private String periodStartTime;

	@Schema(description = "교시 기간", example = "교시 기간")
	private int periodDuration;

	@Schema(description = "교시 출석체크 여부", example = "교시 출석체크 여부")
	@JsonProperty("isAttendanceRequired")
	private boolean isAttendanceRequired;
}
