/**
 *
 */
package com.sivalabs.bookstore.orderservice.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivalabs.bookstore.orderservice.domain.models.OrderCancelledEvent;
import com.sivalabs.bookstore.orderservice.domain.models.OrderCreatedEvent;
import com.sivalabs.bookstore.orderservice.domain.models.OrderDeliveredEvent;
import com.sivalabs.bookstore.orderservice.domain.models.OrderErrorEvent;
import com.sivalabs.bookstore.orderservice.domain.models.OrderEventType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
@Transactional
public class OrderEventService {
    private static final Logger log = LoggerFactory.getLogger(OrderEventService.class);
    private final OrderEventRepository orderEventRepository;
    private final ObjectMapper objectmapper;
    private final OrderEventPublisher orderEventPublisher;

    public OrderEventService(
            OrderEventRepository orderEventRepository,
            ObjectMapper objectmapper,
            OrderEventPublisher orderEventPublisher) {
        this.orderEventRepository = orderEventRepository;
        this.objectmapper = objectmapper;
        this.orderEventPublisher = orderEventPublisher;
    }

    void save(OrderCreatedEvent event) {
        OrderEventEntity orderEvent = new OrderEventEntity();
        orderEvent.setEventId(event.eventId());
        orderEvent.setEventType(OrderEventType.ORDER_CREATED);
        orderEvent.setOrderNumber(event.orderNumber());
        orderEvent.setPayload(toJsonPayload(event));
        orderEvent.setCreatedAt(event.createdAt());
        orderEventRepository.save(orderEvent);
    }

    void save(OrderDeliveredEvent event) {
        OrderEventEntity orderEvent = new OrderEventEntity();
        orderEvent.setEventId(event.eventId());
        orderEvent.setEventType(OrderEventType.ORDER_DELIVERED);
        orderEvent.setOrderNumber(event.orderNumber());
        orderEvent.setCreatedAt(event.createdAt());
        orderEvent.setPayload(toJsonPayload(event));
        this.orderEventRepository.save(orderEvent);
    }

    void save(OrderCancelledEvent event) {
        OrderEventEntity orderEvent = new OrderEventEntity();
        orderEvent.setEventId(event.eventId());
        orderEvent.setEventType(OrderEventType.ORDER_CANCELLED);
        orderEvent.setOrderNumber(event.orderNumber());
        orderEvent.setCreatedAt(event.createdAt());
        orderEvent.setPayload(toJsonPayload(event));
        this.orderEventRepository.save(orderEvent);
    }

    void save(OrderErrorEvent event) {
        OrderEventEntity orderEvent = new OrderEventEntity();
        orderEvent.setEventId(event.eventId());
        orderEvent.setEventType(OrderEventType.ORDER_PROCESSING_FAILED);
        orderEvent.setOrderNumber(event.orderNumber());
        orderEvent.setCreatedAt(event.createdAt());
        orderEvent.setPayload(toJsonPayload(event));
        this.orderEventRepository.save(orderEvent);
    }

    public void publishOrderEvents() {
        Sort sort = Sort.by("createdAt").ascending();
        List<OrderEventEntity> events = orderEventRepository.findAll(sort);
        log.info("Found {} Order Events to be published", events.size());
        for (OrderEventEntity orderEventEntity : events) {
            publishEvent(orderEventEntity);
        }
    }

    private void publishEvent(OrderEventEntity orderEventEntity) {
        OrderEventType eventType = orderEventEntity.getEventType();
        switch (eventType) {
            case ORDER_CREATED:
                OrderCreatedEvent event = fromJsonPayload(orderEventEntity.getPayload(), OrderCreatedEvent.class);
                orderEventPublisher.publish(event);
                break;
            default:
                log.warn("Unsupported OrderEventType: {}", eventType);
        }
    }

    private <T> T fromJsonPayload(String payload, Class<T> class1) {
        try {
            return objectmapper.readValue(payload, class1);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonPayload(Object object) {
        try {
            return objectmapper.writeValueAsString(object);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
