package org.dosilock.clazz.request;

import org.dosilock.timetable.request.TimetableRequest;

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
public class ClazzRequest {
	@Size(min = 2, max = 50, message = "50자를 넘을 수 없음..")
	private String clazzName;
	@Size(max = 1000, message = "1000자를 넘을 수 없음..")
	private String clazzDescription;
	private String clazzIcon;
	private TimetableRequest timetableRequest;
}
