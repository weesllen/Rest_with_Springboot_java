package com.well.demo.integrationtests.testcontainers;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {
	
	 static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{
		 
		static MySQLContainer<?> mysql = new MySQLContainer <> ("mysql:8.0.29")
		 .withDatabaseName("test") 
	        .withUsername("test")  
	        .withPassword("adm123")  
	        .withEnv("Wellsql28.", "adm123");  
		
		private static void StartContainers() {
			Startables.deepStart(Stream.of(mysql)).join();
		}
		
		private static Map <String,String>creatConnectionConfiguration() {
			return Map.of(
					"spring.datasource.url", mysql.getJdbcUrl(),
					"spring.datasource.username", mysql.getUsername(),
					"spring.datasource.password", mysql.getPassword(),
					"spring.flyway.url", mysql.getJdbcUrl(),
		            "spring.flyway.user", mysql.getUsername(),
		            "spring.flyway.password", mysql.getPassword()
					);
		}
		
		@SuppressWarnings({"unchecked","rawtypes"})
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			StartContainers();
			ConfigurableEnvironment enviroment = applicationContext.getEnvironment();
			MapPropertySource testcontainers =  new MapPropertySource(
					"testcontainers",
					(Map)creatConnectionConfiguration());
			enviroment.getPropertySources().addFirst(testcontainers);
		}

		
		}

		
	}


