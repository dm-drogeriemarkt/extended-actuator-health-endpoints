package de.dm.infrastructure.actuator.health.endpoint;

import de.dm.infrastructure.actuator.health.indicator.DetailHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;

public class DetailHealthEndpoint extends ExtendedHealthEndpoint<DetailHealthIndicator> {

    /**
     * Create new DetailHealthEndpoint.
     *
     * @param id               part of the endpoint URL
     * @param healthAggregator usually a new instance of OrderedHealthAggregator
     */
    public DetailHealthEndpoint(String id, HealthAggregator healthAggregator) {
        super(id, healthAggregator, DetailHealthIndicator.class);
    }
}
