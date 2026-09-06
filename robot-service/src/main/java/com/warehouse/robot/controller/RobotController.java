package com.warehouse.robot.controller;

import com.warehouse.robot.dto.RobotRequest;
import com.warehouse.robot.dto.RobotResponse;
import com.warehouse.robot.entity.RobotStatus;
import com.warehouse.robot.service.RobotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API for robot management.
 * Reached via the API Gateway at: http://localhost:8080/api/robots/**
 * Reached directly (for testing) at: http://localhost:8081/api/robots
 */
@RestController
@RequestMapping("/api/robots")
public class RobotController {

    private final RobotService robotService;

    @Autowired
    public RobotController(RobotService robotService) {
        this.robotService = robotService;
    }

    // POST /api/robots - create a new robot
    @PostMapping
    public ResponseEntity<RobotResponse> createRobot(@Valid @RequestBody RobotRequest request) {
        RobotResponse created = robotService.createRobot(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/robots - list all robots, optionally filtered by status
    // e.g. GET /api/robots?status=IDLE
    @GetMapping
    public ResponseEntity<List<RobotResponse>> getAllRobots(
            @RequestParam(required = false) RobotStatus status) {

        if (status != null) {
            return ResponseEntity.ok(robotService.getRobotsByStatus(status));
        }
        return ResponseEntity.ok(robotService.getAllRobots());
    }

    // GET /api/robots/{id} - get one robot
    @GetMapping("/{id}")
    public ResponseEntity<RobotResponse> getRobotById(@PathVariable Long id) {
        return ResponseEntity.ok(robotService.getRobotById(id));
    }

    // PATCH /api/robots/{id}/position - update live position + battery (used by simulation engine)
    @PatchMapping("/{id}/position")
    public ResponseEntity<RobotResponse> updatePosition(
            @PathVariable Long id,
            @RequestParam Integer xPos,
            @RequestParam Integer yPos,
            @RequestParam Integer batteryLevel) {

        return ResponseEntity.ok(robotService.updatePositionAndBattery(id, xPos, yPos, batteryLevel));
    }

    // PATCH /api/robots/{id}/status - change status (e.g. move to CHARGING)
    @PatchMapping("/{id}/status")
    public ResponseEntity<RobotResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam RobotStatus status) {

        return ResponseEntity.ok(robotService.updateStatus(id, status));
    }

    // PATCH /api/robots/{id}/assign-order - assign an order to this robot
    @PatchMapping("/{id}/assign-order")
    public ResponseEntity<RobotResponse> assignOrder(
            @PathVariable Long id,
            @RequestParam Long orderId) {

        return ResponseEntity.ok(robotService.assignOrder(id, orderId));
    }

    // PATCH /api/robots/{id}/clear-order - remove the assigned order (before reassigning it elsewhere)
    @PatchMapping("/{id}/clear-order")
    public ResponseEntity<RobotResponse> clearOrder(@PathVariable Long id) {
        return ResponseEntity.ok(robotService.clearOrder(id));
    }

    // DELETE /api/robots/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRobot(@PathVariable Long id) {
        robotService.deleteRobot(id);
        return ResponseEntity.noContent().build();
    }
}
