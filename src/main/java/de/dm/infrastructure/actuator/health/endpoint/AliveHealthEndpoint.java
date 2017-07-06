package de.dm.infrastructure.actuator.health.endpoint;

import de.dm.infrastructure.actuator.health.indicator.ApplicationAliveIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;

public class AliveHealthEndpoint extends ExtendedHealthEndpoint<ApplicationAliveIndicator> {

    /**
     * Create new AliveHealthEndpoint.
     *
     * @param id               part of the endpoint URL
     * @param healthAggregator usually a new instance of OrderedHealthAggregator
     */
    public AliveHealthEndpoint(String id, HealthAggregator healthAggregator) {
        super(id, healthAggregator, ApplicationAliveIndicator.class);
    }
}
