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
     * @param healthIndicators a map with HealthIndicators
     */
    public DetailHealthEndpoint(String id, HealthAggregator healthAggregator, Map<String, HealthIndicator> healthIndicators) {
        super(id, healthAggregator, healthIndicators);
    }
}
