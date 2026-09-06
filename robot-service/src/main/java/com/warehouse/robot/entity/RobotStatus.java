package com.warehouse.robot.entity;

/**
 * All possible states a robot can be in.
 * IDLE          - waiting, no order assigned
 * MOVING        - traveling to pickup/drop location
 * DELIVERING    - carrying an order to its destination
 * CHARGING      - battery was low, robot is heading to / at a charging station
 */
public enum RobotStatus {
    IDLE,
    MOVING,
    DELIVERING,
    CHARGING
}
