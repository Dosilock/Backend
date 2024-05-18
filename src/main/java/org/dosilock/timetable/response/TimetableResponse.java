package org.dosilock.timetable.response;

import java.util.ArrayList;
import java.util.List;

import org.dosilock.timetable.entity.Timetable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TimetableResponse {

	@Schema(description = "시간표 이름", example = "시간표 이름")
	private String timetableName;

	@Schema(description = "시간표 일수", example = "시간표 일수")
	private List<Integer> timetableDays;

	@Schema(description = "교시")
	private List<PeriodResponse> periodResponses;

	public TimetableResponse(Timetable timetable) {
		this.timetableName = timetable.getTimetableName();
		this.timetableDays = new ArrayList<>();
		this.periodResponses = new ArrayList<>();
	}
}
