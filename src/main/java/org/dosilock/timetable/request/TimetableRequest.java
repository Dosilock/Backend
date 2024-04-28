package org.dosilock.timetable.request;

import java.util.List;

import org.dosilock.timetable.entity.Day;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TimetableRequest {
	private String timetableName;
	private List<Day> day;
	private List<PeriodRequest> periodRequests;
}
