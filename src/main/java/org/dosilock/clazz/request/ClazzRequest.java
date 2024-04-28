package org.dosilock.clazz.request;

import org.dosilock.timetable.request.TimetableRequest;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClazzRequest {
	private String clazzName;
	private String clazzDescription;
	private String clazzIcon;
	private String templateType;
	private TimetableRequest timetableRequest;
}
