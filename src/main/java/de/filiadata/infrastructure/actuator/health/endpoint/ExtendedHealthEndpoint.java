package de.filiadata.infrastructure.actuator.health.endpoint;

import de.filiadata.infrastructure.actuator.health.indicator.ApplicationAliveIndicator;
import de.filiadata.infrastructure.actuator.health.indicator.BasicHealthIndicator;
import de.filiadata.infrastructure.actuator.health.indicator.DetailHealthIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ClassUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * ExtendedHealthEndpoint is meant to aggregate HealthIndicators like Spring Boots HealthEndpoint, but with different levels of HealthIndicators, currently:
 * <p>
 * * {@link ApplicationAliveIndicator} (faster than basic, used by load balancer to decide if application is alive)
 * * {@link BasicHealthIndicator} (only basic, combined should not take longer than 5 seconds)
 * * {@link DetailHealthIndicator} (includes all, combined may take up to 30 seconds)
 *
 * @param <T> HealthIndicator type (either ApplicationAliveIndicator, BasicHealthIndicator or DetailHealthIndicator)
 */
public class ExtendedHealthEndpoint<T extends HealthIndicator> extends AbstractEndpoint<Health> implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(ExtendedHealthEndpoint.class);

    private final HealthAggregator healthAggregator;
    private final Class<T> indicatorMarkerInterface;
    private final Class<? extends HealthIndicator> excludeIndicatorMarkerInterface;
    private ApplicationContext applicationContext;

    /**
     * Create new ExtendedHealthEndpoint.
     *
     * @param id                              part of the endpoint URL
     * @param healthAggregator                usually a new instance of OrderedHealthAggregator
     * @param indicatorMarkerInterface
     * @param excludeIndicatorMarkerInterface indicator class that should be excluded, can be null
     */
    public ExtendedHealthEndpoint(String id, HealthAggregator healthAggregator, Class<T> indicatorMarkerInterface, Class<? extends HealthIndicator> excludeIndicatorMarkerInterface) {
        super(id);
        this.healthAggregator = healthAggregator;
        this.indicatorMarkerInterface = indicatorMarkerInterface;
        this.excludeIndicatorMarkerInterface = excludeIndicatorMarkerInterface;
        LOG.info("Registered ExtendedHealthEndpoint with id " + id);
    }

    /**
     * Create new ExtendedHealthEndpoint.
     *
     * @param id                       part of the endpoint URL
     * @param healthAggregator         usually a new instance of OrderedHealthAggregator
     * @param indicatorMarkerInterface
     */
    public ExtendedHealthEndpoint(String id, HealthAggregator healthAggregator, Class<T> indicatorMarkerInterface) {
        this(id, healthAggregator, indicatorMarkerInterface, null);
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
        for (Map.Entry<String, T> h : collectIndicators().entrySet()) {
            healthIndicator.addHealthIndicator(getKey(h.getKey()), h.getValue());
        }
        return healthIndicator.health();
    }

    private Map<String, T> collectIndicators() {
        Map<String, T> healthIndicators = applicationContext.getBeansOfType(indicatorMarkerInterface);
        if (excludeIndicatorMarkerInterface != null) {
            excludeHealthIndicators(healthIndicators);
        }
        return healthIndicators;
    }

    private void excludeHealthIndicators(Map<String, T> healthIndicators) {
        Set<Map.Entry<String, T>> entries = healthIndicators.entrySet();
        //we have to manually use an iterator so that we can remove entries, done to be java 7 compatible
        Iterator<Map.Entry<String, T>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, T> healthBean = iterator.next();
            if (ClassUtils.isAssignableValue(excludeIndicatorMarkerInterface, healthBean.getValue())) {
                iterator.remove();
            }
        }
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
