package org.dosilock.timetable.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PeriodResponse {
	private String periodName;
	private String periodStartTime;
	private Integer periodDuration;
	@JsonProperty("isAttendanceRequired")
	private Boolean isAttendanceRequired;
}
