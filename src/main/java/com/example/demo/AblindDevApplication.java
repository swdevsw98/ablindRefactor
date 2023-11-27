package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AblindDevApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.properties,"
			+ "classpath:aws.yml";

	public static void main(String[] args) {
//		SpringApplication.run(AblindDevApplication.class, args);
		new SpringApplicationBuilder(AblindDevApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
