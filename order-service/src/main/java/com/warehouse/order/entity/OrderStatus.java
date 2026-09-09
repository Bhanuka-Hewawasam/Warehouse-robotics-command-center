package com.warehouse.order.entity;

/**
 * All possible states an order can be in.
 * PENDING     - just created, no robot assigned yet
 * ASSIGNED    - a robot has been assigned but hasn't started moving it yet
 * IN_PROGRESS - the assigned robot is actively delivering it
 * COMPLETED   - completed successfully
 * CANCELLED   - order was cancelled before delivery
 */
public enum OrderStatus {
    PENDING,
    ASSIGNED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}