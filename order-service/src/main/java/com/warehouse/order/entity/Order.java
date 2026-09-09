package com.warehouse.order.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents one delivery order in the warehouse.
 * Each row in the "orders" table is one item that needs to travel
 * from the warehouse to a destination grid position, carried by a robot.
 */
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "item_description", nullable = false)
    private String itemDescription;

    @Column(name = "pickup_x", nullable = false)
    private Integer pickupX; // grid X coordinate to collect the item from

    @Column(name = "pickup_y", nullable = false)
    private Integer pickupY; // grid Y coordinate to collect the item from

    @Column(name = "drop_x", nullable = false)
    private Integer dropX; // grid X coordinate to deliver to

    @Column(name = "drop_y", nullable = false)
    private Integer dropY; // grid Y coordinate to deliver to

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "assigned_robot_id")
    private Long assignedRobotId; // nullable - null means no robot assigned yet

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    // ---- Constructors ----

    public Order() {
        // Required by JPA
    }

    public Order(String customerName, String itemDescription,
                 Integer pickupX, Integer pickupY, Integer dropX, Integer dropY) {
        this.customerName = customerName;
        this.itemDescription = itemDescription;
        this.pickupX = pickupX;
        this.pickupY = pickupY;
        this.dropX = dropX;
        this.dropY = dropY;
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }

    // ---- Lifecycle hooks ----

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

    // ---- Getters and Setters ----

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