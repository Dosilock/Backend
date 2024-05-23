package org.dosilock.clazz.response;

import org.dosilock.clazz.entity.FocusTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FocusTimeResponse {

	private String nickname;
	private Long timestamp;

	public FocusTimeResponse(FocusTime focusTime) {
		this.nickname = focusTime.getMember().getNickname();
		this.timestamp = focusTime.getTimestamp();
	}
}
