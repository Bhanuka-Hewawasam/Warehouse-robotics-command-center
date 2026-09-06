package com.warehouse.robot.service;

/**
 * Thrown when a robot ID doesn't exist in the database.
 * Caught by RobotController and converted into a 404 response.
 */
public class RobotNotFoundException extends RuntimeException {
    public RobotNotFoundException(Long id) {
        super("Robot not found with id: " + id);
    }
}
