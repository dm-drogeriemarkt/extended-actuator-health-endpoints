package de.filiadata.infrastructure.actuator.health.endpoint;

import de.filiadata.infrastructure.actuator.health.indicator.ApplicationAliveIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;

import java.util.Map;

public class AliveHealthEndpoint extends ExtendedHealthEndpoint<ApplicationAliveIndicator> {

    /**
     * Create new AliveHealthEndpoint.
     *
     * @param id
     * @param healthAggregator
     * @param healthIndicators
     */
    public AliveHealthEndpoint(String id, HealthAggregator healthAggregator, Map<String, ApplicationAliveIndicator> healthIndicators) {
        super(id, healthAggregator, healthIndicators);
    }
}
