package org.dosilock.timetable.response;

import java.util.List;

import lombok.Getter;

@Getter
public class TimetableResponse {
	private String timetableName;
	private List<Integer> days;
	private List<PeriodResponse> periodRequests;
}
