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
	private Day day;
	private String periodName;
	private String periodStartTime;
	private String periodEndTime;
	private String recessStartTime;
	private String recessEndTime;
	@JsonProperty("attendanceRequired")
	private boolean attendanceRequired;
	private int periodType;
}
