package org.dosilock.clazz.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClazzListResponse {
	private String clazzName;
	@JsonProperty("isOwned")
	private boolean isOwned;
	private int memberCount;
}
