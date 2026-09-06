package com.warehouse.robot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * What the client sends us when creating a new robot via POST /api/robots.
 * Kept separate from the Robot entity so the API contract doesn't break
 * if we change internal database fields later.
 *
 * NOTE: getXPos()/getYPos() have two capital letters right after "get",
 * so the default Java Bean naming rule leaves the property name as
 * "XPos"/"YPos" instead of decapitalizing to "xPos"/"yPos". Without the
 * @JsonProperty overrides below, Jackson won't match the lowercase JSON
 * keys the frontend/Postman sends, and the fields silently stay null.
 */
public class RobotRequest {

    @NotBlank(message = "name is required")
    private String name;

    @JsonProperty("xPos")
    @NotNull(message = "xPos is required")
    private Integer xPos;

    @JsonProperty("yPos")
    @NotNull(message = "yPos is required")
    private Integer yPos;

    @NotNull(message = "batteryLevel is required")
    @Min(value = 0, message = "batteryLevel must be between 0 and 100")
    @Max(value = 100, message = "batteryLevel must be between 0 and 100")
    private Integer batteryLevel;

    public RobotRequest() {
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
}