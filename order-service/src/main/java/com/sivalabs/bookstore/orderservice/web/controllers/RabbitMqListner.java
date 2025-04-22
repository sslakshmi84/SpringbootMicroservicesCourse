/**
 *
 */
package com.sivalabs.bookstore.orderservice.web.controllers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class RabbitMqListner {

	/*
	 * @RabbitListener(queues = "${orders.new-orders-queue}") public void
	 * handleNewOrders(MyPayload payload) { System.out.println("new order:" +
	 * payload.content()); }
	 * 
	 * @RabbitListener(queues = "${orders.delivered-orders-queue}") public void
	 * handleDeliveryOrders(MyPayload payload) {
	 * System.out.println("Delivery order:" + payload.content()); }
	 */
}
