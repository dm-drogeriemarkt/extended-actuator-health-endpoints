package de.filiadata.infrastructure.actuator.health.endpoint;

import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.Map;

public class DetailHealthEndpoint extends ExtendedHealthEndpoint<HealthIndicator> {

    /**
     * Create new DetailHealthEndpoint.
     *
     * @param id
     * @param healthAggregator
     * @param healthIndicators
     */
    public DetailHealthEndpoint(String id, HealthAggregator healthAggregator, Map<String, HealthIndicator> healthIndicators) {
        super(id, healthAggregator, healthIndicators);
    }
}
