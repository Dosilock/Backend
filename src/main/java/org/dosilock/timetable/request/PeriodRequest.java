package org.dosilock.timetable.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PeriodRequest {
	private String periodName;
	private String periodStartTime;
	private String periodEndTime;
	private String recessStartTime;
	private String recessEndTime;
	@JsonProperty("attendanceRequired")
	private boolean attendanceRequired;
	private int periodType;
}
