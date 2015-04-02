package de.filiadata.infrastructure.actuator.health.endpoint;

import de.filiadata.infrastructure.actuator.health.indicator.BasicHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;

import java.util.Map;

public class BasicHealthEndpoint extends ExtendedHealthEndpoint<BasicHealthIndicator> {

    /**
     * Create new BasicHealthEndpoint.
     *
     * @param id
     * @param healthAggregator
     * @param healthIndicators
     */
    public BasicHealthEndpoint(String id, HealthAggregator healthAggregator, Map<String, BasicHealthIndicator> healthIndicators) {
        super(id, healthAggregator, healthIndicators);
    }
}
