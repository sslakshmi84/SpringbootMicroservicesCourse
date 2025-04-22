/**
 *
 */
package com.sivalabs.bookstore.orderservice.domain;

import com.sivalabs.bookstore.orderservice.clients.catalog.Product;
import com.sivalabs.bookstore.orderservice.clients.catalog.ProductServiceClient;
import com.sivalabs.bookstore.orderservice.domain.models.CreateOrderRequest;
import com.sivalabs.bookstore.orderservice.domain.models.OrderItem;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class OrderValidator {
    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);
    ProductServiceClient productServiceClient;

    OrderValidator(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    void validate(CreateOrderRequest request) {

        Set<OrderItem> items = request.items();
        for (OrderItem item : items) {
            log.info("order{} details in order service", item);
            Product product = productServiceClient
                    .getProductByCode(item.code())
                    .orElseThrow(() -> new InvalidOrderException("Invalid Product code:" + item.code()));
            log.info("product{} details in order service", product);
            if (product.price().compareTo(item.price()) != 0) {
                log.error(
                        "Product price not matching. Actual price:{}, received price:{}",
                        product.price(),
                        item.price());
                throw new InvalidOrderException("Product price not matching");
            }
        }
    }
}
