/**
 * 
 */
package com.sivalabs.bookstore.catalog.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import com.sivalabs.bookstore.catalog.AbstaractIT;
import com.sivalabs.bookstore.catalog.domain.Product;

import io.restassured.http.ContentType;

/**
 * 
 */
@Sql("/test-data.sql")
public class ProductControllerTest extends AbstaractIT{
	
	@Test 
	 void shouldReturnProducts() {
		given().contentType(ContentType.JSON)
		       .when()
		       .get("/api/products")
		       .then()
		       .statusCode(200)
		       .body("data", hasSize(10))
		       .body("totalElements", is(15))
               .body("pageNumber", is(1))
               .body("totalPages", is(2))
               .body("isFirst", is(true))
               .body("isLast", is(false))
               .body("hasNext", is(true))
               .body("hasPrevious", is(false));
	} 
	
	@Test
    void shouldGetProductByCode() {
        Product product = given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/{code}", "P100")
                .then()
                .statusCode(200)
                .assertThat()
                .extract()
                .body()
                .as(Product.class);

        assertThat(product.code()).isEqualTo("P100");
        assertThat(product.name()).isEqualTo("The Hunger Games");
        assertThat(product.description()).isEqualTo("Winning will make you famous. Losing means certain death...");
        assertThat(product.price()).isEqualTo(new BigDecimal("34.00"));
    }

    @Test
    void shouldReturnNotFoundWhenProductCodeNotExists() {
        String code = "invalid_product_code";
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/{code}", code)
                .then()
                .statusCode(404)
                .body("errorCode", is(404))
                .body("errorMessage", is("Product with code " + code + " not found"))
                .body("errorStatus", is("NOT_FOUND"));
    }

}
