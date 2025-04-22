/**
 *
 */
package com.sivalabs.bookstore.orderservice.domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.bookstore.orderservice.domain.models.CreateOrderRequest;
import com.sivalabs.bookstore.orderservice.domain.models.CreateOrderResponse;
import com.sivalabs.bookstore.orderservice.domain.models.OrderCreatedEvent;
import com.sivalabs.bookstore.orderservice.domain.models.OrderStatus;

import jakarta.validation.Valid;

/**
 *
 */
@Service
@Transactional
public class OrderService {
	private static final Logger log = LoggerFactory.getLogger(OrderService.class);
	private static final List<String> DELIVERY_ALLOWED_COUNTRIES = List.of("INDIA", "USA", "GERMANY", "UK");

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderEventService orderEventService;

    OrderService(OrderRepository orderRepository, OrderValidator orderValidator, OrderEventService orderEventService) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderEventService = orderEventService;
    }

    public CreateOrderResponse createOrder(String username, @Valid CreateOrderRequest request) {
        orderValidator.validate(request);
        OrderEntity newOrder = OrderMapper.convertToEntity(request);
        newOrder.setUserName(username);
        OrderEntity saveOrder = orderRepository.save(newOrder);
        log.info("created order with order id={}", saveOrder.getOrderNumber());

        OrderCreatedEvent orderCreatedEvent = OrderEventMapper.buildOrderCreatedEvent(saveOrder);
        orderEventService.save(orderCreatedEvent);
        return new CreateOrderResponse(saveOrder.getOrderNumber());
    }

	public void processNewOrders() {
		List<OrderEntity> orders = orderRepository.findByStatus(OrderStatus.NEW);
		 log.info("Found {} new orders to process", orders.size());
		 for(OrderEntity order:orders) {
		 process(order);
		 }
		
	}

	private void process(OrderEntity order) {
		try {
		if(canBeDelivered(order)) {
			 log.info("OrderNumber: {} can be delivered", order.getOrderNumber());
			 orderRepository.updateOrderStatus(order.getOrderNumber(),OrderStatus.DELIVERED);
			 orderEventService.save(OrderEventMapper.buildOrderDeliveredEvent(order));
		}else {
			log.info("OrderNumber: {} can not be delivered", order.getOrderNumber());
            orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.CANCELLED);
            orderEventService.save(
                    OrderEventMapper.buildOrderCancelledEvent(order, "Can't deliver to the location"));
		}
		}
		catch(RuntimeException e) {
			 log.error("Failed to process Order with orderNumber: {}", order.getOrderNumber(), e);
			 orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.ERROR);
	         orderEventService.save(OrderEventMapper.buildOrderErrorEvent(order, e.getMessage()));
		}
		
	}

	private boolean canBeDelivered(OrderEntity order) {
		return DELIVERY_ALLOWED_COUNTRIES.contains(
                order.getDeliveryAddress().country().toUpperCase());
		
	}
}
