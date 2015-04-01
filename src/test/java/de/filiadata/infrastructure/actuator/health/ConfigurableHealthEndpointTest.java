package de.filiadata.infrastructure.actuator.health;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.health.*;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigurableHealthEndpointTest {

    private HealthAggregator healthAggregator;
    private HashMap<String, HealthIndicator> healthIndicators = new HashMap<>();

    @Before
    public void init() {
        healthAggregator = new OrderedHealthAggregator();
        healthIndicators.put("FooHealthIndicator", new HealthIndicator() {
            @Override
            public Health health() {
                return Health.up().build();
            }
        });
        healthIndicators.put("Bar", new HealthIndicator() {
            @Override
            public Health health() {
                return Health.down().build();
            }
        });
    }

    @Test
    public void up() throws Exception {

        ExtendedHealthEndpoint basic = new ExtendedHealthEndpoint("test", healthAggregator, healthIndicators);
        assertThat(basic.invoke().getStatus(), is(Status.DOWN));
    }
}
