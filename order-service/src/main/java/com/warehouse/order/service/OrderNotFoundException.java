package com.warehouse.order.service;

/**
 * Thrown when an order ID doesn't exist in the database.
 * Caught by OrderController and converted into a 404 response.
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order not found with id: " + id);
    }
}
