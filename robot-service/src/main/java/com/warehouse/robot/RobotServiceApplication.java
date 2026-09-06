package com.warehouse.robot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Owns all robot state: position, battery, status, assigned order.
 * Registers with Eureka as "robot-service" so the API Gateway and other
 * services (like order-service) can find it without a hardcoded URL.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class RobotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RobotServiceApplication.class, args);
    }
}
