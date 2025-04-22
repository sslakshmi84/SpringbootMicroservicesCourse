/**
 *
 */
package com.sivalabs.bookstore.orderservice.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sivalabs.bookstore.orderservice.domain.models.OrderStatus;

/**
 *
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	
	
	List<OrderEntity> findByStatus(OrderStatus orderStatus);
	Optional<OrderEntity> findByOrderNumber(String orderNumber);
	default void updateOrderStatus(String orderNumber,OrderStatus status) {
		
		 this.findByOrderNumber(orderNumber).orElseThrow();
		 OrderEntity order = this.findByOrderNumber(orderNumber).orElseThrow();
	     order.setStatus(status);
	     this.save(order);
		
	}
	
	}
