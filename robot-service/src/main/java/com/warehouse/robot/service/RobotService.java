package com.warehouse.robot.service;

import com.warehouse.robot.dto.RobotRequest;
import com.warehouse.robot.dto.RobotResponse;
import com.warehouse.robot.entity.Robot;
import com.warehouse.robot.entity.RobotStatus;
import com.warehouse.robot.repository.RobotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * All business logic for robots lives here. The Controller only handles
 * HTTP concerns (status codes, request/response mapping) and delegates
 * the actual work to this class.
 */
@Service
public class RobotService {

    private final RobotRepository robotRepository;

    @Autowired
    public RobotService(RobotRepository robotRepository) {
        this.robotRepository = robotRepository;
    }

    // Create a new robot, starting as IDLE
    public RobotResponse createRobot(RobotRequest request) {
        if (robotRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("A robot named '" + request.getName() + "' already exists");
        }

        Robot robot = new Robot(
                request.getName(),
                request.getXPos(),
                request.getYPos(),
                request.getBatteryLevel(),
                RobotStatus.IDLE
        );

        Robot saved = robotRepository.save(robot);
        return RobotResponse.fromEntity(saved);
    }

    // Get every robot in the system
    public List<RobotResponse> getAllRobots() {
        return robotRepository.findAll()
                .stream()
                .map(RobotResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // Get a single robot by ID, or throw if it doesn't exist
    public RobotResponse getRobotById(Long id) {
        Robot robot = findRobotOrThrow(id);
        return RobotResponse.fromEntity(robot);
    }

    // Get every robot currently in a given status (e.g. all IDLE robots)
    public List<RobotResponse> getRobotsByStatus(RobotStatus status) {
        return robotRepository.findByStatus(status)
                .stream()
                .map(RobotResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // Update a robot's live position and battery level (called by the simulation engine)
    public RobotResponse updatePositionAndBattery(Long id, Integer xPos, Integer yPos, Integer batteryLevel) {
        Robot robot = findRobotOrThrow(id);
        robot.setXPos(xPos);
        robot.setYPos(yPos);
        robot.setBatteryLevel(batteryLevel);
        Robot saved = robotRepository.save(robot);
        return RobotResponse.fromEntity(saved);
    }

    // Change a robot's status (e.g. IDLE -> MOVING, or MOVING -> CHARGING)
    public RobotResponse updateStatus(Long id, RobotStatus newStatus) {
        Robot robot = findRobotOrThrow(id);
        robot.setStatus(newStatus);
        Robot saved = robotRepository.save(robot);
        return RobotResponse.fromEntity(saved);
    }

    // Assign an order to a robot
    public RobotResponse assignOrder(Long id, Long orderId) {
        Robot robot = findRobotOrThrow(id);
        robot.setCurrentOrderId(orderId);
        robot.setStatus(RobotStatus.MOVING);
        Robot saved = robotRepository.save(robot);
        return RobotResponse.fromEntity(saved);
    }

    // Clear a robot's assigned order (e.g. after delivery completes, or before reassignment)
    public RobotResponse clearOrder(Long id) {
        Robot robot = findRobotOrThrow(id);
        robot.setCurrentOrderId(null);
        Robot saved = robotRepository.save(robot);
        return RobotResponse.fromEntity(saved);
    }

    public void deleteRobot(Long id) {
        if (!robotRepository.existsById(id)) {
            throw new RobotNotFoundException(id);
        }
        robotRepository.deleteById(id);
    }

    // Shared lookup used by every method above
    private Robot findRobotOrThrow(Long id) {
        return robotRepository.findById(id)
                .orElseThrow(() -> new RobotNotFoundException(id));
    }
}
