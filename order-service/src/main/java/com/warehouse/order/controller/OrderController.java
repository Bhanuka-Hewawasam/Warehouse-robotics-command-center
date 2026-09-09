package com.warehouse.order.controller;

import com.warehouse.order.dto.OrderRequest;
import com.warehouse.order.dto.OrderResponse;
import com.warehouse.order.entity.OrderStatus;
import com.warehouse.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API for order management.
 * Reached via the API Gateway at: http://localhost:8080/api/orders/**
 * Reached directly (for testing) at: http://localhost:8082/api/orders
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // POST /api/orders - create a new order
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse created = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/orders - list all orders, optionally filtered by status
    // e.g. GET /api/orders?status=PENDING
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(
            @RequestParam(required = false) OrderStatus status) {

        if (status != null) {
            return ResponseEntity.ok(orderService.getOrdersByStatus(status));
        }
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // GET /api/orders/{id} - get one order
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // PATCH /api/orders/{id}/assign-robot - assign a robot to this order.
    // Talks to robot-service under the hood via Eureka service discovery.
    @PatchMapping("/{id}/assign-robot")
    public ResponseEntity<OrderResponse> assignRobot(
            @PathVariable Long id,
            @RequestParam Long robotId) {

        return ResponseEntity.ok(orderService.assignRobot(id, robotId));
    }

    // PATCH /api/orders/{id}/status - move the order along its lifecycle
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {

        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }

    // DELETE /api/orders/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
