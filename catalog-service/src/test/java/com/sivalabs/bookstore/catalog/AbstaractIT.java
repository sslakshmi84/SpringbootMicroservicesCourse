/**
 * 
 */
package com.sivalabs.bookstore.catalog;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import io.restassured.RestAssured;

/**
 * 
 */
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstaractIT {
	
	@LocalServerPort
	int port;
	@BeforeEach
	void setUp() {
		RestAssured.port=port;
		
	}
	

}
