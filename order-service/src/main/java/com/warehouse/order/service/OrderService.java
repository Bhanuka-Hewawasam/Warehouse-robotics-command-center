package com.warehouse.order.service;

import com.warehouse.order.dto.OrderRequest;
import com.warehouse.order.dto.OrderResponse;
import com.warehouse.order.entity.Order;
import com.warehouse.order.entity.OrderStatus;
import com.warehouse.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * All business logic for orders lives here. The Controller only handles
 * HTTP concerns (status codes, request/response mapping) and delegates
 * the actual work to this class.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    // Create a new order, starting as PENDING with no robot assigned
    public OrderResponse createOrder(OrderRequest request) {
        Order order = new Order(
                request.getCustomerName(),
                request.getItemDescription(),
                request.getPickupX(),
                request.getPickupY(),
                request.getDropX(),
                request.getDropY()
        );

        Order saved = orderRepository.save(order);
        return OrderResponse.fromEntity(saved);
    }

    // Get every order in the system
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // Get a single order by ID, or throw if it doesn't exist
    public OrderResponse getOrderById(Long id) {
        Order order = findOrderOrThrow(id);
        return OrderResponse.fromEntity(order);
    }

    // Get every order currently in a given status (e.g. all PENDING orders)
    public List<OrderResponse> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status)
                .stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // Assign a robot to this order. Calls robot-service's own assign-order
    // endpoint (PATCH /api/robots/{robotId}/assign-order) via Eureka discovery
    // so both services agree on who is doing what - order-service doesn't
    // touch the robots table directly.
    public OrderResponse assignRobot(Long orderId, Long robotId) {
        Order order = findOrderOrThrow(orderId);

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalArgumentException(
                    "Order " + orderId + " is already " + order.getStatus() + " - cannot assign a robot");
        }

        String url = "http://ROBOT-SERVICE/api/robots/{robotId}/assign-order?orderId={orderId}";

        try {
            restTemplate.patchForObject(url, null, Void.class, robotId, orderId);
        } catch (RestClientException ex) {
            throw new IllegalStateException(
                    "Could not reach robot-service to assign robot " + robotId + ": " + ex.getMessage());
        }

        order.setAssignedRobotId(robotId);
        order.setStatus(OrderStatus.ASSIGNED);
        Order saved = orderRepository.save(order);
        return OrderResponse.fromEntity(saved);
    }

    // Move an order along its lifecycle (e.g. ASSIGNED -> IN_PROGRESS -> COMPLETED)
    public OrderResponse updateStatus(Long id, OrderStatus newStatus) {
        Order order = findOrderOrThrow(id);
        order.setStatus(newStatus);
        Order saved = orderRepository.save(order);
        return OrderResponse.fromEntity(saved);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
    }

    // Shared lookup used by every method above
    private Order findOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}