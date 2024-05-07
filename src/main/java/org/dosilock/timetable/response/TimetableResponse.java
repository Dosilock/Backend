package org.dosilock.timetable.response;

import java.util.ArrayList;
import java.util.List;

import org.dosilock.timetable.entity.Timetable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TimetableResponse {
	private String timetableName;
	private List<Integer> timetableDays;
	private List<PeriodResponse> periodResponses;

	public TimetableResponse(Timetable timetable) {
		this.timetableName = timetable.getTimetableName();
		this.timetableDays = new ArrayList<>();
		this.periodResponses = new ArrayList<>();
	}
}
