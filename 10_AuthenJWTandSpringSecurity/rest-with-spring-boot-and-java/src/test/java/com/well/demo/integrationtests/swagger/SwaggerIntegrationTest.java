package com.well.demo.integrationtests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.well.demo.configs.TestsConfig;
import com.well.demo.integrationtests.testcontainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void shoudDisplaySwaggerUiPage() {
		var content =
			given()
				 .basePath("/swagger-ui/index.html")
				 .port(TestsConfig.SERVER_PORT)
				 .when()
				     .get()
				.then()
				     .statusCode(200)
				.extract()
			    	.body()
				         .asString();
		assertTrue(content.contains("swagger UI"));
			
	}

}
