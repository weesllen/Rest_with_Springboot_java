package com.well.demo.integrationtests.controller.withJson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.well.demo.configs.TestsConfig;
import com.well.demo.integrationtests.vo.AccountCredentialsVO;
import com.well.demo.integrationtests.vo.TokenVO;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthControllerJsonTest {

	private static TokenVO tokenVO;
	
	@Test
	@Order(0)
	public void testSignin() throws JsonMappingException,JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro","admin123"); 
		
		tokenVO = given()
				.contentType("/auth/signin")
				.port(TestsConfig.SERVER_PORT)
				.contentType(TestsConfig.CONTENT_TYPE_JSON)
					.body(user)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(TokenVO.class);
		
			
		assertNotNull(tokenVO.getAccessToken());
		assertNotNull(tokenVO.getRefreshToken());
	}
	
	@Test
	@Order(1)
	public void testRefresh() throws JsonMappingException,JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro","admin123"); 
		
		var newTokenVO = given()
				.contentType("/auth/refresh")
				.port(TestsConfig.SERVER_PORT)
				.contentType(TestsConfig.CONTENT_TYPE_JSON)
					.pathParam("username", tokenVO.getUsername())
					.header(TestsConfig.HEADER_PARAM_ORIGIN, "Bearer "+tokenVO.getRefreshToken())
				.when()
					.put("{username}")
				.then()
					.statusCode(200)
				.extract()
					.body()
							.as(TokenVO.class);
		
			
		assertNotNull(newTokenVO.getAccessToken());
		assertNotNull(newTokenVO.getRefreshToken());
	}
}
