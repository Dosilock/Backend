package org.dosilock.timetable.request;

import org.dosilock.timetable.entity.Day;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	private String periodName;
	private String periodStartTime;
	private int periodDuration;
	@JsonProperty("isAttendanceRequired")
	private boolean isAttendanceRequired;
}
