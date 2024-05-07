package org.dosilock.timetable.response;

import java.time.LocalTime;

import org.dosilock.timetable.entity.Period;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class PeriodResponse {
	private String periodName;
	private LocalTime periodStartTime;
	private Integer periodDuration;
	@JsonProperty("isAttendanceRequired")
	private Boolean isAttendanceRequired;

	public PeriodResponse(Period period) {
		this.periodName = period.getPeriodName();
		this.periodStartTime = period.getPeriodStartTime();
		this.periodDuration = period.getPeriodDuration();
		this.isAttendanceRequired = period.isAttendanceRequired();
	}
}
