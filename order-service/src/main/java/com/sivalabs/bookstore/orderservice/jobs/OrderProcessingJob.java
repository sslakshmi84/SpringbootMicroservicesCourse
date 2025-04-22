/**
 * 
 */
package com.sivalabs.bookstore.orderservice.jobs;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sivalabs.bookstore.orderservice.domain.OrderService;

import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

/**
 * 
 */
@Component
public class OrderProcessingJob {
	Logger log =LoggerFactory.getLogger(OrderProcessingJob.class);
	private final OrderService orderService;
	
	public OrderProcessingJob(OrderService orderService) {
		this.orderService=orderService;
	}
	
	@Scheduled(cron = "${orders.new-orders-job-cron}")
    @SchedulerLock(name = "processNewOrders")
    public void processNewOrders() {
        LockAssert.assertLocked();
        log.info("Processing new orders at {}", Instant.now());
        orderService.processNewOrders();
    }

}
