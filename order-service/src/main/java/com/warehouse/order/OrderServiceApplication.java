package com.warehouse.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Owns all order state: what's being delivered, where, and which robot
 * (if any) is carrying it. Registers with Eureka as "order-service" so
 * the API Gateway can route to it, and so this service can look up
 * robot-service by name when assigning robots to orders.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
