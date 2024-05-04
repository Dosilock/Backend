package org.dosilock.timetable.request;

import java.util.List;
import org.dosilock.timetable.entity.Day;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimetableRequest {
	@Size(min = 2, max = 50, message = "50자를 넘길 수 없음..")
	private String timetableName;
	private List<Integer> days;
	private List<PeriodRequest> periodRequests;
}
