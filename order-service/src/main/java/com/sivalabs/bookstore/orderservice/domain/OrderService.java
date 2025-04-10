/**
 *
 */
package com.sivalabs.bookstore.orderservice.domain;

import com.sivalabs.bookstore.orderservice.domain.models.CreateOrderRequest;
import com.sivalabs.bookstore.orderservice.domain.models.CreateOrderResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
@Transactional
public class OrderService {

    private OrderRepository orderRepository;

    OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CreateOrderResponse createOrder(String username, @Valid CreateOrderRequest request) {
        OrderEntity newOrder = OrderMapper.convertToEntity(request);
        newOrder.setUserName(username);
        OrderEntity saveOrder = orderRepository.save(newOrder);
        return new CreateOrderResponse(saveOrder.getOrderNumber());
    }
}
