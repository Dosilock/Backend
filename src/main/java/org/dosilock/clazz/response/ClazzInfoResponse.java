package org.dosilock.clazz.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClazzInfoResponse {
	private String clazzName;
	private String clazzIcon;
	private int memberCount;
}
