package org.dosilock.timetable.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TimetableRequest {

	@Schema(description = "시간표 이름", example = "시간표 이름")
	@Size(min = 2, max = 50, message = "50자를 넘길 수 없음..")
	private String timetableName;

	@Schema(description = "시간표 일수", example = "시간표 일수")
	private List<Integer> dayOfWeeks;

	@Schema(description = "교시")
	private List<PeriodRequest> periodRequests;
}
