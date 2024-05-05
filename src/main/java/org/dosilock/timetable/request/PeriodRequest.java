package org.dosilock.timetable.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PeriodRequest {
	private String periodName;
	private String periodStartTime;
	private Integer periodDuration;
	@JsonProperty("isAttendanceRequired")
	private Boolean isAttendanceRequired;
}
