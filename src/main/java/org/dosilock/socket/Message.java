package org.dosilock.socket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {
	private String payload;

	public Message(String payload) {
		this.payload = payload;
	}
}
