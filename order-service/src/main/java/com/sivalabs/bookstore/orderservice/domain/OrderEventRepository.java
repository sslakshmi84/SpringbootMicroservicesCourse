/**
 *
 */
package com.sivalabs.bookstore.orderservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface OrderEventRepository extends JpaRepository<OrderEventEntity, Long> {}
