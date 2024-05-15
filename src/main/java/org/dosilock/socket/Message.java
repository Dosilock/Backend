package org.dosilock.socket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {
	private MessageType type;
	private String message;

	public Message(MessageType type, String message) {
		this.type = type;
		this.message = message;
	}
}
