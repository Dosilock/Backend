package org.dosilock.timetable.request;

import java.util.List;

import org.dosilock.clazz.request.ClazzRequest;
import org.dosilock.timetable.entity.Day;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TimetableRequest extends ClazzRequest {
	@Size(min = 2, max = 50, message = "50자를 넘길 수 없음..")
	private String timetableName;
	private List<Day> day;
}
