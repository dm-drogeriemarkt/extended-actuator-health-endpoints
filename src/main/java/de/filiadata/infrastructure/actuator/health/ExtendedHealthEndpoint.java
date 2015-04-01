package de.filiadata.infrastructure.actuator.health;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.Map;

/**
 * ConfigurableHealthEndpoint is meant to aggreagte HealthIndicators like Spring Boots HealthEndpoint, but with different levels of HealthIndicators, currently:
 * <p/>
 * * {@link ApplicationAliveIndicator} (faster than basic, used by load balancer to decide if application is alive)
 * * {@link BasicHealthIndicator} (only basic, combined should not take longer than 5 seconds)
 * * {@link DetailHealthIndicator} (includes all, combined may take up to 30 seconds)
 *
 * @param <T>
 */
public class ExtendedHealthEndpoint<T extends HealthIndicator> extends AbstractEndpoint<Health> {

    private final HealthAggregator healthAggregator;
    private final Map<String, T> healthIndicators;

    /**
     * Create new ConfigurableHealthEndpoint.
     *
     * @param id
     * @param healthAggregator
     * @param healthIndicators
     */
    public ExtendedHealthEndpoint(String id, HealthAggregator healthAggregator, Map<String, T> healthIndicators) {
        super(id);
        this.healthAggregator = healthAggregator;
        this.healthIndicators = healthIndicators;
    }

    @Override
    public Health invoke() {
        CompositeHealthIndicator healthIndicator = new CompositeHealthIndicator(
                healthAggregator);
        for (Map.Entry<String, T> h : healthIndicators.entrySet()) {
            healthIndicator.addHealthIndicator(getKey(h.getKey()), h.getValue());
        }
        return healthIndicator.health();
    }

    /**
     * Turns the bean name into a key that can be used in the map of health information.
     */
    private String getKey(String name) {
        int index = name.toLowerCase().indexOf("healthindicator");
        if (index > 0) {
            return name.substring(0, index);
        }
        return name;
    }
}
