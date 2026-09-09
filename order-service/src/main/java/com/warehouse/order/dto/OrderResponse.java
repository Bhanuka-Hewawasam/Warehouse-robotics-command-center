package com.warehouse.order.dto;

import com.warehouse.order.entity.Order;
import com.warehouse.order.entity.OrderStatus;
import java.time.LocalDateTime;

/**
 * What we send back to the client for any order-returning endpoint.
 */
public class OrderResponse {

    private Long id;
    private String customerName;
    private String itemDescription;
    private Integer pickupX;
    private Integer pickupY;
    private Integer dropX;
    private Integer dropY;
    private OrderStatus status;
    private Long assignedRobotId;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    public OrderResponse() {
    }

    // Maps an Order entity straight into a response - keeps this conversion
    // logic in one place instead of repeating it all over the service layer.
    public static OrderResponse fromEntity(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerName(order.getCustomerName());
        response.setItemDescription(order.getItemDescription());
        response.setPickupX(order.getPickupX());
        response.setPickupY(order.getPickupY());
        response.setDropX(order.getDropX());
        response.setDropY(order.getDropY());
        response.setStatus(order.getStatus());
        response.setAssignedRobotId(order.getAssignedRobotId());
        response.setCreatedAt(order.getCreatedAt());
        response.setLastUpdated(order.getLastUpdated());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getPickupX() {
        return pickupX;
    }

    public void setPickupX(Integer pickupX) {
        this.pickupX = pickupX;
    }

    public Integer getPickupY() {
        return pickupY;
    }

    public void setPickupY(Integer pickupY) {
        this.pickupY = pickupY;
    }

    public Integer getDropX() {
        return dropX;
    }

    public void setDropX(Integer dropX) {
        this.dropX = dropX;
    }

    public Integer getDropY() {
        return dropY;
    }

    public void setDropY(Integer dropY) {
        this.dropY = dropY;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getAssignedRobotId() {
        return assignedRobotId;
    }

    public void setAssignedRobotId(Long assignedRobotId) {
        this.assignedRobotId = assignedRobotId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}