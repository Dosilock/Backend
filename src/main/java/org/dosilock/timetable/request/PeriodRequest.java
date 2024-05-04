package org.dosilock.timetable.request;

import org.dosilock.timetable.entity.Day;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class PeriodRequest {
	private String periodName;
	private String periodStartTime;
	private String periodEndTime;
	private String recessStartTime;
	private String recessEndTime;
	@JsonProperty("isAttendanceRequired")
	private boolean isAttendanceRequired;
	private int periodType;
}
