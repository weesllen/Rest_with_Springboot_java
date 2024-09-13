package com.well.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Restful API with Java 21 and Spring boot 3")
						.version("v1")
						.description("My first API created by Spring boot !!")
						.termsOfService("https://www.google.com.br/?hl=pt-BR")
							.license(
								new License()
								.name("Apache 2.0")
								.url("http://www.apache.org/licenses/")
								));
	}

}
