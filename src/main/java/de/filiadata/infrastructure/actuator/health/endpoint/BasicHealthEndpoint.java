package de.filiadata.infrastructure.actuator.health.endpoint;

import de.filiadata.infrastructure.actuator.health.indicator.DetailHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;

public class BasicHealthEndpoint extends ExtendedHealthEndpoint<HealthIndicator> {

    /**
     * Create new BasicHealthEndpoint.
     *
     * @param id               part of the endpoint URL
     * @param healthAggregator usually a new instance of OrderedHealthAggregator
     */
    public BasicHealthEndpoint(String id, HealthAggregator healthAggregator) {
        super(id, healthAggregator, HealthIndicator.class, DetailHealthIndicator.class);
    }
}
