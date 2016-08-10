package de.filiadata.infrastructure.actuator.health.endpoint;

import de.filiadata.infrastructure.actuator.health.indicator.ApplicationAliveIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;

import java.util.Map;

public class AliveHealthEndpoint extends ExtendedHealthEndpoint<ApplicationAliveIndicator> {

    /**
     * Create new AliveHealthEndpoint.
     *
     * @param id part of the endpoint URL
     * @param healthAggregator usually a new instance of OrderedHealthAggregator
     */
    public AliveHealthEndpoint(String id, HealthAggregator healthAggregator) {
        super(id, healthAggregator, ApplicationAliveIndicator.class);
    }
}
