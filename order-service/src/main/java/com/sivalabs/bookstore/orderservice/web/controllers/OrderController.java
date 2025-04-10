/**
 *
 */
package com.sivalabs.bookstore.orderservice.web.controllers;

import com.sivalabs.bookstore.orderservice.domain.OrderService;
import com.sivalabs.bookstore.orderservice.domain.SecurityService;
import com.sivalabs.bookstore.orderservice.domain.models.CreateOrderRequest;
import com.sivalabs.bookstore.orderservice.domain.models.CreateOrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("api/orders")
public class OrderController {

    private OrderService orderService;
    private SecurityService securityService;

    OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@RequestBody @Valid CreateOrderRequest request) {
        String username = securityService.loginUserName();
        return orderService.createOrder(username, request);
    }
}
