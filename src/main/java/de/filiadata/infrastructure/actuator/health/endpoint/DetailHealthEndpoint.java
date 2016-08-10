package de.filiadata.infrastructure.actuator.health.endpoint;

import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.Map;

public class DetailHealthEndpoint extends ExtendedHealthEndpoint<HealthIndicator> {

    /**
     * Create new DetailHealthEndpoint.
     *
     * @param id part of the endpoint URL
     * @param healthAggregator usually a new instance of OrderedHealthAggregator
     */
    public DetailHealthEndpoint(String id, HealthAggregator healthAggregator) {
        super(id, healthAggregator, HealthIndicator.class);
    }
}
