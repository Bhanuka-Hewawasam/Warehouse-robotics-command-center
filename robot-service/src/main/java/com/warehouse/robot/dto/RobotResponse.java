package com.warehouse.robot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.warehouse.robot.entity.Robot;
import com.warehouse.robot.entity.RobotStatus;
import java.time.LocalDateTime;

/**
 * What we send back to the client. Built from a Robot entity via the
 * static fromEntity() factory method below.
 */
public class RobotResponse {

    private Long id;
    private String name;
    private Integer xPos;
    private Integer yPos;
    private Integer batteryLevel;
    private RobotStatus status;
    private Long currentOrderId;
    private LocalDateTime lastUpdated;

    public RobotResponse() {
    }

    // Converts a Robot entity into a RobotResponse for the API
    public static RobotResponse fromEntity(Robot robot) {
        RobotResponse response = new RobotResponse();
        response.id = robot.getId();
        response.name = robot.getName();
        response.xPos = robot.getXPos();
        response.yPos = robot.getYPos();
        response.batteryLevel = robot.getBatteryLevel();
        response.status = robot.getStatus();
        response.currentOrderId = robot.getCurrentOrderId();
        response.lastUpdated = robot.getLastUpdated();
        return response;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("xPos")
    public Integer getXPos() {
        return xPos;
    }

    @JsonProperty("yPos")
    public Integer getYPos() {
        return yPos;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public RobotStatus getStatus() {
        return status;
    }

    public Long getCurrentOrderId() {
        return currentOrderId;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
}