package org.dosilock.clazz.request;

import org.dosilock.timetable.request.TimetableRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClazzRequest {

	@Size(min = 2, max = 50, message = "50자를 넘을 수 없음..")
	@Schema(description = "반 이름")
	private String clazzName;

	@Size(max = 1000, message = "1000자를 넘을 수 없음..")
	@Schema(description = "반 설명")
	private String clazzDescription;

	@Schema(description = "반 아이콘")
	private String clazzIcon;
	
	@Schema(description = "시간표 요청 DTO")
	private TimetableRequest timetableRequest;
}
