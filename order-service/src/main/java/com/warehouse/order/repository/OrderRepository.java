package com.warehouse.order.repository;

import com.warehouse.order.entity.Order;
import com.warehouse.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA generates the implementation of this interface at runtime.
 * We only declare method signatures - Spring translates the method name into SQL.
 *
 * e.g. findByStatus(OrderStatus.PENDING) becomes:
 *      SELECT * FROM orders WHERE status = 'PENDING'
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Used by the dashboard to show orders still waiting for a robot
    List<Order> findByStatus(OrderStatus status);

    // Used to look up every order currently carried by a specific robot
    List<Order> findByAssignedRobotId(Long robotId);
}
