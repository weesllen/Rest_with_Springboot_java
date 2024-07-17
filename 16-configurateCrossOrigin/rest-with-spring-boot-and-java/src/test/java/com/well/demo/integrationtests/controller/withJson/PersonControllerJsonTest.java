package com.well.demo.integrationtests.controller.withJson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.well.demo.configs.TestsConfig;
import com.well.demo.integrationtests.testcontainers.AbstractIntegrationTest;
import com.well.demo.integrationtests.vo.PersonVO;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static PersonVO person;
		
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNEXPECTED_VIEW_PROPERTIES);
		
	}
	
	@Test
	@Order(1)
	public void create () throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestsConfig.HEADER_PARAM_ORIGIN, "https://well.com.br")
				.setBasePath("/api/person/v1")
				.setPort(TestsConfig.SERVER_PORT)
					 .addFilter(new RequestLoggingFilter(LogDetail.ALL))
					 .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
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
		
			PersonVO createPerson = objectMapper.readValue(content, PersonVO.class);
			person = createPerson;
			
			assertNotNull(createPerson);
			assertNotNull(createPerson.getId());
			assertNotNull(createPerson.getId());
			assertNotNull(createPerson.getFirstName());
			assertNotNull(createPerson.getLastName());
			assertNotNull(createPerson.getAddress());
			assertNotNull(createPerson.getGender());
			
			assertTrue(createPerson.getId() > 0);
			
			assertEquals("Danilo",createPerson.getFirstName());
			assertEquals("Santana",createPerson.getLastName());
			assertEquals("Brasil",createPerson.getAddress());
			assertEquals("Male",createPerson.getGender());
			
	}

	private void mockPerson() {
		
		person.setFirstName("Danilo");
		person.setLastName("Santana");
		person.setAddress("Brasil");
		person.setGender("Male");
	}

}
