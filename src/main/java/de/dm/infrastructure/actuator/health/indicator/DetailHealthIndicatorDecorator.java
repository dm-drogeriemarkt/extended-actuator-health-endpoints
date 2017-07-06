package de.dm.infrastructure.actuator.health.indicator;

import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * Wraps a regular {@link HealthIndicator} to expose it as an {@link DetailHealthIndicator},
 * e.g. to wrap a {@link CompositeHealthIndicator}.
 *
 * @author Jakob Fels
 */
public class DetailHealthIndicatorDecorator implements BasicHealthIndicator {

    private final HealthIndicator delegate;

    public DetailHealthIndicatorDecorator(HealthIndicator delegate) {
        this.delegate = delegate;
    }

    @Override
    public Health health() {
        return delegate.health();
    }
}
