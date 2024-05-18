package org.dosilock.timetable.response;

import java.time.LocalTime;

import org.dosilock.timetable.entity.Period;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PeriodResponse {

	@Schema(description = "교시 이름", example = "교시 이름")
	private String periodName;

	@Schema(description = "교시 시작 시간", example = "교시 시작 시간")
	private LocalTime periodStartTime;

	@Schema(description = "교시 기간")
	private Integer periodDuration;

	@Schema(description = "교시 출석체크 여부", example = "교시 출석체크 여부")
	@JsonProperty("isAttendanceRequired")
	private Boolean isAttendanceRequired;

	public PeriodResponse(Period period) {
		this.periodName = period.getPeriodName();
		this.periodStartTime = period.getPeriodStartTime();
		this.periodDuration = period.getPeriodDuration();
		this.isAttendanceRequired = period.isAttendanceRequired();
	}
}
