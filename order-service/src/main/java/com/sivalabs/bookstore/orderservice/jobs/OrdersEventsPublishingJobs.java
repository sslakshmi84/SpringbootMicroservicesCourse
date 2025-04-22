/**
 *
 */
package com.sivalabs.bookstore.orderservice.jobs;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sivalabs.bookstore.orderservice.domain.OrderEventService;

import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

/**
 *
 */
@Component
public class OrdersEventsPublishingJobs {

    private static final Logger log = LoggerFactory.getLogger(OrdersEventsPublishingJobs.class);

    private final OrderEventService orderEventService;

    public OrdersEventsPublishingJobs(OrderEventService orderEventService) {
        this.orderEventService = orderEventService;
    }

    @SchedulerLock(name = "publishOrderEvents")
    public void publishOrderEvents() {
        LockAssert.assertLocked();
        log.info("Publishing Order Events at {}", Instant.now());
        orderEventService.publishOrderEvents();
    }
}
