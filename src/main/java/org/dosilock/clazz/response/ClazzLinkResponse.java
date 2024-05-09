package org.dosilock.clazz.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClazzLinkResponse {
	
	@Schema(description = "반 고유링크")
	private String clazzLink;
}
