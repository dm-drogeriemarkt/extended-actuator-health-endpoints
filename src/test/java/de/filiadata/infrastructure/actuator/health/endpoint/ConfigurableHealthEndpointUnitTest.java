package de.filiadata.infrastructure.actuator.health.endpoint;

import de.filiadata.infrastructure.actuator.health.indicator.DetailHealthIndicator;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.*;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigurableHealthEndpointUnitTest {

    @Test
    public void testLazyHealthIndicatorLookup() throws Exception {

        HashMap<String, HealthIndicator> healthIndicators = new HashMap<>();
        HealthAggregator healthAggregator = new OrderedHealthAggregator();
        healthIndicators.put("FooHealthIndicator", () -> Health.up().build());
        healthIndicators.put("Bar", () -> Health.down().build());

        ApplicationContext applicationContext = Mockito.mock(ApplicationContext.class);
        Mockito.when(applicationContext.getBeansOfType(HealthIndicator.class)).thenReturn(healthIndicators);

        Class<? extends HealthIndicator> basicHealthIndicatorClass = HealthIndicator.class;
        ExtendedHealthEndpoint basic = new ExtendedHealthEndpoint<>("test", healthAggregator, basicHealthIndicatorClass);
        basic.setApplicationContext(applicationContext);

        assertThat(basic.invoke().getStatus(), is(Status.DOWN));

    }

    @Test
    public void testIndicatorExclusion() throws Exception {

        HashMap<String, HealthIndicator> healthIndicators = new HashMap<>();
        HealthAggregator healthAggregator = new OrderedHealthAggregator();
        healthIndicators.put("foo", new MyBasicHealthIndicator());
        healthIndicators.put("Bar", new MyDetailHealthIndicator());

        ApplicationContext applicationContext = Mockito.mock(ApplicationContext.class);
        Mockito.when(applicationContext.getBeansOfType(HealthIndicator.class)).thenReturn(healthIndicators);

        ExtendedHealthEndpoint<HealthIndicator> basic = new ExtendedHealthEndpoint<>("test", healthAggregator, HealthIndicator.class, DetailHealthIndicator.class);
        basic.setApplicationContext(applicationContext);

        assertThat(basic.invoke().getStatus(), is(Status.UP));

    }

    private static class MyDetailHealthIndicator implements DetailHealthIndicator {
        @Override
        public Health health() {
            return Health.down().build();
        }
    }

    private static class MyBasicHealthIndicator implements HealthIndicator {
        @Override
        public Health health() {
            return Health.up().build();
        }
    }
}
