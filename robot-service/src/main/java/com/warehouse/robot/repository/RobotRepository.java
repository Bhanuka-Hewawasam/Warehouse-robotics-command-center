package com.warehouse.robot.repository;

import com.warehouse.robot.entity.Robot;
import com.warehouse.robot.entity.RobotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA generates the implementation of this interface at runtime.
 * We only declare method signatures - Spring translates the method name into SQL.
 *
 * e.g. findByStatus(RobotStatus.IDLE) becomes:
 *      SELECT * FROM robots WHERE status = 'IDLE'
 */
public interface RobotRepository extends JpaRepository<Robot, Long> {

    // Used by the reassignment logic to find an available robot
    List<Robot> findByStatus(RobotStatus status);

    // Used to check if a robot name is already taken before creating a new one
    boolean existsByName(String name);

    // Used by the battery-drain scheduler to find robots that need attention
    List<Robot> findByBatteryLevelLessThan(Integer batteryLevel);
}
