package com.warehouse.robot.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents one AGV (Automated Guided Vehicle) in the warehouse.
 * Each row in the "robots" table is one physical robot.
 */
@Entity
@Table(name = "robots")
public class Robot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // e.g. "Robot-01"

    @Column(name = "x_pos", nullable = false)
    private Integer xPos; // grid X coordinate

    @Column(name = "y_pos", nullable = false)
    private Integer yPos; // grid Y coordinate

    @Column(name = "battery_level", nullable = false)
    private Integer batteryLevel; // 0-100

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RobotStatus status;

    @Column(name = "current_order_id")
    private Long currentOrderId; // nullable - null means no order assigned

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    // ---- Constructors ----

    public Robot() {
        // Required by JPA
    }

    public Robot(String name, Integer xPos, Integer yPos, Integer batteryLevel, RobotStatus status) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.batteryLevel = batteryLevel;
        this.status = status;
        this.lastUpdated = LocalDateTime.now();
    }

    // ---- Lifecycle hooks ----

    @PrePersist
    @PreUpdate
    protected void onSave() {
        this.lastUpdated = LocalDateTime.now();
    }

    // ---- Getters and Setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getXPos() {
        return xPos;
    }

    public void setXPos(Integer xPos) {
        this.xPos = xPos;
    }

    public Integer getYPos() {
        return yPos;
    }

    public void setYPos(Integer yPos) {
        this.yPos = yPos;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public RobotStatus getStatus() {
        return status;
    }

    public void setStatus(RobotStatus status) {
        this.status = status;
    }

    public Long getCurrentOrderId() {
        return currentOrderId;
    }

    public void setCurrentOrderId(Long currentOrderId) {
        this.currentOrderId = currentOrderId;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
