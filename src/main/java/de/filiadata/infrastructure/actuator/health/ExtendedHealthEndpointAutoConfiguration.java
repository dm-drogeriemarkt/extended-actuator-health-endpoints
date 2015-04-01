package de.filiadata.infrastructure.actuator.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.OrderedHealthAggregator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(ExtendedHealthProperties.class)
public class ExtendedHealthEndpointAutoConfiguration {

    @Autowired(required = false)
    public Map<String, ApplicationAliveIndicator> aliveIndicators = new HashMap<>();

    @Autowired
    private ExtendedHealthProperties properties;

    @Bean
    public ExtendedHealthEndpoint<ApplicationAliveIndicator> applicationAliveEndpoint() {

        return new ExtendedHealthEndpoint<>(properties.getAliveId(), new OrderedHealthAggregator(), aliveIndicators);
    }

    @Autowired(required = false)
    public Map<String, BasicHealthIndicator> basicHealthIndicators = new HashMap<>();

    @Bean
    public ExtendedHealthEndpoint<BasicHealthIndicator> basicHealthEndpoint() {

        return new ExtendedHealthEndpoint<>(properties.getBasicId(), new OrderedHealthAggregator(), basicHealthIndicators);
    }

    @Autowired(required = false)
    public Map<String, HealthIndicator> allHealthIndicators = new HashMap<>();

    @Bean
    public ExtendedHealthEndpoint<HealthIndicator> detailHealthEndpoint() {

        return new ExtendedHealthEndpoint<>(properties.getDetailId(), new OrderedHealthAggregator(), allHealthIndicators);
    }
}
