package de.filiadata.infrastructure.actuator.health.endpoint;

import de.filiadata.infrastructure.actuator.health.indicator.ApplicationAliveIndicator;
import de.filiadata.infrastructure.actuator.health.indicator.BasicHealthIndicator;
import de.filiadata.infrastructure.actuator.health.indicator.DetailHealthIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.Map;

/**
 * ExtendedHealthEndpoint is meant to aggregate HealthIndicators like Spring Boots HealthEndpoint, but with different levels of HealthIndicators, currently:
 *
 * * {@link ApplicationAliveIndicator} (faster than basic, used by load balancer to decide if application is alive)
 * * {@link BasicHealthIndicator} (only basic, combined should not take longer than 5 seconds)
 * * {@link DetailHealthIndicator} (includes all, combined may take up to 30 seconds)
 *
 * @param <T> HealthIndicator type (either ApplicationAliveIndicator, BasicHealthIndicator or DetailHealthIndicator)
 */
public class ExtendedHealthEndpoint<T extends HealthIndicator> extends AbstractEndpoint<Health> {

    private static final Logger LOG = LoggerFactory.getLogger(ExtendedHealthEndpoint.class);

    private final HealthAggregator healthAggregator;
    private final Map<String, T> healthIndicators;

    /**
     * Create new ExtendedHealthEndpoint.
     *
     * @param id part of the endpoint URL
     * @param healthAggregator usually a new instance of OrderedHealthAggregator
     * @param healthIndicators a map with HealthIndicators
     */
    public ExtendedHealthEndpoint(String id, HealthAggregator healthAggregator, Map<String, T> healthIndicators) {
        super(id);
        this.healthAggregator = healthAggregator;
        this.healthIndicators = healthIndicators;
        LOG.info("Registered ExtendedHealthEndpoint with id " + id);
    }

    @Override
    public Health invoke() {
        Health health;
        try {
            health = internalInvokeHealthIndicators();
        } catch (Exception e) {
            LOG.error("Unexpected error in HealthIndicator:", e);
            health = Health.down(e).build();
        }
        return health;
    }

    private Health internalInvokeHealthIndicators() {
        CompositeHealthIndicator healthIndicator = new CompositeHealthIndicator(
                healthAggregator);
        for (Map.Entry<String, T> h : healthIndicators.entrySet()) {
            healthIndicator.addHealthIndicator(getKey(h.getKey()), h.getValue());
        }
        return healthIndicator.health();
    }

    /**
     * Turns the bean name into a key that can be used in the map of health information.
     *
     * @param name the key cuts 'healthindicator' from the name
     */
    private String getKey(String name) {
        int index = name.toLowerCase().indexOf("healthindicator");
        if (index > 0) {
            return name.substring(0, index);
        }
        return name;
    }
}
