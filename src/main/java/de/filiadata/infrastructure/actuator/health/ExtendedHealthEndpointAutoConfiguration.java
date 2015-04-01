package de.filiadata.infrastructure.actuator.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * ExtendedHealthEndpointAutoConfiguration configures HealthIndicators like Spring Boots HealthEndpoint, but with different levels of HealthIndicators, currently:
 * <p/>
 * * {@link ApplicationAliveIndicator} (faster than basic, used by load balancer to decide if application is alive)
 * * {@link BasicHealthIndicator} (only basic, combined should not take longer than 5 seconds)
 * * {@link DetailHealthIndicator} (includes all, combined may take up to 30 seconds)
 * <p/>
 * Beans implementing one of these Interfaces are automatically included in one of these health endpoint categories.
 */
@Configuration
public class ExtendedHealthEndpointAutoConfiguration {

    @Autowired(required = false)
    public Map<String, ApplicationAliveIndicator> aliveIndicators = new HashMap<>();

    @Bean
    public ExtendedHealthEndpoint<ApplicationAliveIndicator> applicationAliveEndpoint(HealthAggregator healthAggregator) {

        return new ExtendedHealthEndpoint<>("health/ApplicationAliveCheck", healthAggregator, aliveIndicators);
    }

    @Autowired(required = false)
    public Map<String, BasicHealthIndicator> basicHealthIndicators = new HashMap<>();

    @Bean
    public ExtendedHealthEndpoint<BasicHealthIndicator> basicHealthEndpoint(
            HealthAggregator healthAggregator) {

        return new ExtendedHealthEndpoint<>("health/HealthBasicCheck", healthAggregator, basicHealthIndicators);
    }

    @Autowired(required = false)
    public Map<String, HealthIndicator> allHealthIndicators = new HashMap<>();

    @Bean
    public ExtendedHealthEndpoint<HealthIndicator> detailHealthEndpoint(HealthAggregator healthAggregator) {

        return new ExtendedHealthEndpoint<>("health/HealthDetailCheck", healthAggregator, allHealthIndicators);
    }
}
