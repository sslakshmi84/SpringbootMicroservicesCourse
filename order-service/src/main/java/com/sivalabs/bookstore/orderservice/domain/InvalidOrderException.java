/**
 *
 */
package com.sivalabs.bookstore.orderservice.domain;

/**
 *
 */
public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(String message) {
        super(message);
    }
}
