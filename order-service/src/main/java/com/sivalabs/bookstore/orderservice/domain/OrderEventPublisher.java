package com.sivalabs.bookstore.orderservice.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.sivalabs.bookstore.orderservice.config.ApplicationProperties;
import com.sivalabs.bookstore.orderservice.domain.models.OrderCreatedEvent;

@Component
public class OrderEventPublisher {
	Logger log=  LoggerFactory.getLogger(OrderEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate, ApplicationProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void publish(OrderCreatedEvent event) {
        this.send(properties.newOrdersQueue(), event);
    }

    private void send(String routingKey, Object payload) {
    	log.info("routingKey in OrderEventPublisher :" + routingKey);
    	log.info("payload in OrderEventPublisher :" + payload);
    	log.info("Exchange in OrderEventPublisher :" + properties.orderEventsExchange());
    		rabbitTemplate.convertAndSend(properties.orderEventsExchange(), routingKey, payload);
    }
}
