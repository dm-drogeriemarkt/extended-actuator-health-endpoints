package de.filiadata.infrastructure.actuator.health.endpoint;

import de.filiadata.infrastructure.actuator.health.indicator.DetailHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.Map;

public class DetailHealthEndpoint extends ExtendedHealthEndpoint<DetailHealthIndicator> {

    /**
     * Create new DetailHealthEndpoint.
     *
     * @param id part of the endpoint URL
     * @param healthAggregator usually a new instance of OrderedHealthAggregator
     */
    public DetailHealthEndpoint(String id, HealthAggregator healthAggregator) {
        super(id, healthAggregator, DetailHealthIndicator.class);
    }
}
