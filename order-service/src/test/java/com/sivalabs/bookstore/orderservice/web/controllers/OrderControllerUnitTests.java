/**
 *
 */
package com.sivalabs.bookstore.orderservice.web.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivalabs.bookstore.orderservice.domain.OrderService;
import com.sivalabs.bookstore.orderservice.domain.SecurityService;
import com.sivalabs.bookstore.orderservice.domain.models.Address;
import com.sivalabs.bookstore.orderservice.domain.models.CreateOrderRequest;
import com.sivalabs.bookstore.orderservice.domain.models.CreateOrderResponse;
import com.sivalabs.bookstore.orderservice.domain.models.Customer;
import com.sivalabs.bookstore.orderservice.domain.models.OrderItem;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 *
 */
@WebMvcTest(OrderController.class)
public class OrderControllerUnitTests {

    @MockBean
    private OrderService orderservice;

    @MockBean
    private SecurityService securityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        given(securityService.loginUserName()).willReturn("siva");
    }

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {
        // 1. Prepare request object
        Set<OrderItem> items = Set.of(new OrderItem("Name", "code", new BigDecimal(1.0), 1));
        Customer customer = new Customer("John Doe", "john@example.com", "1234567890");
        Address address = new Address("123 Street", "Apt 4B", "New York", "NY", "10001", "USA");

        CreateOrderRequest request = new CreateOrderRequest(items, customer, address);

        // 2. Prepare expected response
        CreateOrderResponse response = new CreateOrderResponse("ORD1234");
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
