/**
 *
 */
package com.sivalabs.bookstore.orderservice.web.controllers;

import com.sivalabs.bookstore.orderservice.config.ApplicationProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class RabbitMqDemoController {

    private RabbitTemplate rabbitTemplate;
    private ApplicationProperties properties;

    public RabbitMqDemoController(RabbitTemplate rabbitTemplate, ApplicationProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    @PostMapping("/post")
    public void sendMessage(@RequestBody MyMessage message) {
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), message.routingKey(), message.payload());
    }
}

record MyMessage(String routingKey, MyPayload payload) {}

record MyPayload(String content) {}
