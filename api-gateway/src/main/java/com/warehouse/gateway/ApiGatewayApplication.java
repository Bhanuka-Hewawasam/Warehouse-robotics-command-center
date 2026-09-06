package com.warehouse.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Single entry point for the React dashboard.
 * Routes /api/robots/**   -> robot-service
 *        /api/orders/**   -> order-service
 *        /ws/**           -> notification-service (WebSocket)
 *
 * Frontend only ever talks to this gateway (localhost:8080), never to the
 * individual services directly. This keeps CORS config and auth in ONE place.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
