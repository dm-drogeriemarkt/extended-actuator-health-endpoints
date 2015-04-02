package de.filiadata.infrastructure.actuator.health.indicator;

import de.filiadata.infrastructure.actuator.health.endpoint.ExtendedHealthEndpoint;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * Marker interface for fast health indicators that should be available in /health/alive.
 * <p/>
 * Application Alive indicators should be as fast as possible
 * and is used by load balancers to decide if the application is alive.
 *
 * @see ExtendedHealthEndpoint
 */
public interface ApplicationAliveIndicator extends HealthIndicator {
}
