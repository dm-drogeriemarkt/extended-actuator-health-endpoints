package de.filiadata.infrastructure.actuator.health.endpoint;

import de.filiadata.infrastructure.actuator.health.endpoint.mvc.ExtendedHealthMvcEndpoint;
import de.filiadata.infrastructure.actuator.health.indicator.ApplicationAliveIndicator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExtendedHealthMvcEndpointUnitTest {

    private AliveHealthEndpoint extendedHealthEndpoint;
    private ExtendedHealthMvcEndpoint extendedHealthMvcEndpoint;
    private ApplicationContext applicationContext;

    @Before
    public void init() {
        this.extendedHealthEndpoint = mock(AliveHealthEndpoint.class);
        this.extendedHealthMvcEndpoint = new ExtendedHealthMvcEndpoint(this.extendedHealthEndpoint);

        this.applicationContext = mock(ApplicationContext.class);
        this.extendedHealthEndpoint.setApplicationContext(applicationContext);
        when(extendedHealthEndpoint.isEnabled()).thenReturn(true);
    }

    @Test
    public void up() {

        when(extendedHealthEndpoint.invoke()).thenReturn(new Health.Builder().up().build());

        ResponseEntity<Health> result = (ResponseEntity<Health>) this.extendedHealthMvcEndpoint.invoke(null);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody().getStatus(), is(Status.UP));
    }

    @Test
    public void downWithExceptionHandling() throws Exception {

        HealthAggregator healthAggregator = mock(HealthAggregator.class);
        ApplicationAliveIndicator healthIndicator = mock(ApplicationAliveIndicator.class);
        when(healthIndicator.health()).thenThrow(new RuntimeException("fooException"));
        Map<String, ApplicationAliveIndicator> healthIndicators = new LinkedHashMap<>();
        healthIndicators.put("foo", healthIndicator);

        when(applicationContext.getBeansOfType(ApplicationAliveIndicator.class)).thenReturn(healthIndicators);


        extendedHealthEndpoint = new AliveHealthEndpoint("healthIndicatorfoo", healthAggregator);
        extendedHealthEndpoint.setApplicationContext(applicationContext);

        this.extendedHealthMvcEndpoint = new ExtendedHealthMvcEndpoint(this.extendedHealthEndpoint);

        ResponseEntity<Health> result = (ResponseEntity<Health>) this.extendedHealthMvcEndpoint.invoke(null);

        Map<String, Object> expectedHealthDetails = new LinkedHashMap<>();
        expectedHealthDetails.put("error", "java.lang.RuntimeException: fooException");
        assertThat(result.getBody().getDetails(), is(expectedHealthDetails));
        assertThat(result.getStatusCode(), is(HttpStatus.SERVICE_UNAVAILABLE));
        assertThat(result.getBody().getStatus(), is(Status.DOWN));
    }

    @Test
    public void down() {

        when(extendedHealthEndpoint.invoke()).thenReturn(new Health.Builder().down().build());

        ResponseEntity<Health> result = (ResponseEntity<Health>) this.extendedHealthMvcEndpoint.invoke(null);

        assertThat(result.getStatusCode(), is(HttpStatus.SERVICE_UNAVAILABLE));
        assertThat(result.getBody().getStatus(), is(Status.DOWN));
    }


}
