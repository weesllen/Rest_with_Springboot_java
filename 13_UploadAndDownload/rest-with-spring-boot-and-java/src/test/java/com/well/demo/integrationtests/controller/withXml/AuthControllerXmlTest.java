package com.well.demo.integrationtests.controller.withXml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.well.demo.configs.TestConfigs;
import com.well.demo.integrationtests.vo.AccountCredentialsVO;
import com.well.demo.integrationtests.vo.TokenVO;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthControllerXmlTest {

	private static TokenVO tokenVO;
	
	@Test
	@Order(0)
	public void testSignin() throws JsonMappingException,JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro","admin123"); 
		
		tokenVO = given()
				.contentType("/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
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
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
					.pathParam("username", tokenVO.getUsername())
					.header(TestConfigs.HEADER_PARAM_ORIGIN, "Bearer "+tokenVO.getRefreshToken())
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
