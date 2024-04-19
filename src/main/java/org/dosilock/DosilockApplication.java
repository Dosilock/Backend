package org.dosilock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DosilockApplication {

	public static void main(String[] args) {
		SpringApplication.run(DosilockApplication.class, args);
	}

}
