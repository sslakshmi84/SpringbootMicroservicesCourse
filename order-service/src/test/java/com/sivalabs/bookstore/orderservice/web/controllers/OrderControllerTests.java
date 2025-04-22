/**
 *
 */
package com.sivalabs.bookstore.orderservice.web.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

import com.sivalabs.bookstore.orderservice.AbstaractIT;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 *
 */
public class OrderControllerTests extends AbstaractIT {
    @Nested
    class createOrderTest {
        @Test
        void shouldCreateOrderSuccessfully() {
            var payload =
                    """
                    {
                        "customer" : {
                            "name": "Siva",
                            "email": "siva@gmail.com",
                            "phone": "999999999"
                        },
                        "deliveryAddress" : {
                            "addressLine1": "HNO 123",
                            "addressLine2": "Kukatpally",
                            "city": "Hyderabad",
                            "state": "Telangana",
                            "zipCode": "500072",
                            "country": "India"
                        },
                        "items": [
                            {
                                "code": "P100",
                                "name": "Product 1",
                                "price": 34.0,
                                "quantity": 1
                            }
                        ]
                    }
                """;

            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            var payload =
                    """
                    {
                        "customer" : {
                            "name": "Siva",
                            "email": "siva@gmail.com",
                            "phone": ""
                        },
                        "deliveryAddress" : {
                            "addressLine1": "HNO 123",
                            "addressLine2": "Kukatpally",
                            "city": "Hyderabad",
                            "state": "Telangana",
                            "zipCode": "500072",
                            "country": "India"
                        },
                        "items": [
                            {
                                "code": "P100",
                                "name": "Product 1",
                                "price": 25.50,
                                "quantity": 1
                            }
                        ]
                    }
                """;
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
