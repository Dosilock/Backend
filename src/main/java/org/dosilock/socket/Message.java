package org.dosilock.socket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {
	private Object payload;

	public Message(Object payload) {
		this.payload = payload;
	}
}
