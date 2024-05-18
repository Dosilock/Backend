package org.dosilock.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	info = @Info(title = "공시락 API 명세서",
		description = "시간표를 공유해보자!!",
		version = "v1",
		contact = @Contact(name = "권동휘", email = "hocci0222@kakao.com"))
)
@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi api() {
		return GroupedOpenApi.builder()
			.group("default")
			.packagesToScan("org.dosilock")
			.build();
	}
}
