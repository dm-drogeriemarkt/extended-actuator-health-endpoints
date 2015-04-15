package de.filiadata.infrastructure.actuator.health.indicator;

import de.filiadata.infrastructure.actuator.health.endpoint.ExtendedHealthEndpoint;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * Marker interface for detailed health indicators that should be available in /health/detail.
 *
 * Detailed Health indicators shouldn't take longer than 30 seconds aggregated.
 *
 * @see ExtendedHealthEndpoint
 */
public interface DetailHealthIndicator extends HealthIndicator {
}
