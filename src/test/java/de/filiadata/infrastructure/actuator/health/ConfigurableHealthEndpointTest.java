package de.filiadata.infrastructure.actuator.health;

import de.filiadata.infrastructure.actuator.health.endpoint.ExtendedHealthEndpoint;
import de.filiadata.infrastructure.actuator.health.indicator.BasicHealthIndicator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.*;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigurableHealthEndpointTest {


    @Test
    public void down() throws Exception {

        HashMap<String, HealthIndicator> healthIndicators = new HashMap<>();
        HealthAggregator healthAggregator = new OrderedHealthAggregator();
        healthIndicators.put("FooHealthIndicator", () -> Health.up().build());
        healthIndicators.put("Bar", () -> Health.down().build());

        ApplicationContext applicationContext = Mockito.mock(ApplicationContext.class);
        Mockito.when(applicationContext.getBeansOfType(HealthIndicator.class)).thenReturn(healthIndicators);

        Class<? extends HealthIndicator> basicHealthIndicatorClass = HealthIndicator.class;
        ExtendedHealthEndpoint basic = new ExtendedHealthEndpoint("test", healthAggregator, basicHealthIndicatorClass);
        basic.setApplicationContext(applicationContext);

        assertThat(basic.invoke().getStatus(), is(Status.DOWN));

    }
}
