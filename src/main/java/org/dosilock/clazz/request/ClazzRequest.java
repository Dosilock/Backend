package org.dosilock.clazz.request;

import org.dosilock.timetable.request.TimetableRequest;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClazzRequest {
	@Size(min = 2, max = 50, message = "50자를 넘을 수 없음..")
	private String clazzName;
	@Size(max = 1000, message = "1000자를 넘을 수 없음..")
	private String clazzDescription;
	private String clazzIcon;
	private TimetableRequest timetableRequest;
}
