package de.filiadata.infrastructure.actuator.health;

/**
 * Marker interface for detailed health indicators that should be available in /health/HealthDetailCheck.
 * <p/>
 * Detailed Health indicators shouldn't take longer than 30 seconds aggregated.
 *
 * @see BasicHealthIndicator
 * @see ExtendedHealthEndpoint
 */
public interface DetailHealthIndicator extends BasicHealthIndicator {
}
