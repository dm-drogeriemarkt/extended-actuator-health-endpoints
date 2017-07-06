package de.dm.infrastructure.actuator.health.indicator;

import de.dm.infrastructure.actuator.health.endpoint.ExtendedHealthEndpoint;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * Marker interface for detailed health indicators that should be available in /health/detail.
 * <p>
 * Detailed Health indicators shouldn't take longer than 30 seconds aggregated.
 *
 * @see ExtendedHealthEndpoint
 */
public interface DetailHealthIndicator extends HealthIndicator {
}
