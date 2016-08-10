package de.filiadata.infrastructure.actuator.health.endpoint;

import de.filiadata.infrastructure.actuator.health.indicator.BasicHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;

import java.util.Map;

public class BasicHealthEndpoint extends ExtendedHealthEndpoint<BasicHealthIndicator> {

    /**
     * Create new BasicHealthEndpoint.
     *
     * @param id part of the endpoint URL
     * @param healthAggregator usually a new instance of OrderedHealthAggregator
     */
    public BasicHealthEndpoint(String id, HealthAggregator healthAggregator) {
        super(id, healthAggregator, BasicHealthIndicator.class);
    }
}
