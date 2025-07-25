package com.fitnesstracker.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI apiInfo() {
		return new OpenAPI().info(new Info().title("Fitness Tracker API")
				.description("REST API to manage users, workout plans, and activity logs").version("1.0.0"));
	}

	@Bean
	GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("fitness-api").pathsToMatch("/v1/**").build();
	}

}
