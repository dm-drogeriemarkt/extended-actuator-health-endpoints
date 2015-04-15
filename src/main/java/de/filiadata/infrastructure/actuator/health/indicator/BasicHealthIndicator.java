package de.filiadata.infrastructure.actuator.health.indicator;

import de.filiadata.infrastructure.actuator.health.endpoint.ExtendedHealthEndpoint;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * Marker interface for fast health indicators that should be available in /health/basic.
 *
 * Basic Health indicators shouldn't take longer than 5 seconds aggregated.
 *
 * @see ExtendedHealthEndpoint
 */
public interface BasicHealthIndicator extends HealthIndicator {
}
