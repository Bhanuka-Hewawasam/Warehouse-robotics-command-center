package com.warehouse.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * A RestTemplate that resolves service names (like "ROBOT-SERVICE") through
 * Eureka instead of needing a hardcoded host:port. @LoadBalanced is what
 * enables this - it lets us call http://ROBOT-SERVICE/api/robots/... and
 * have Spring Cloud look up the actual address from the service registry.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
