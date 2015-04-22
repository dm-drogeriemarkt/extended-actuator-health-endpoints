package de.filiadata.infrastructure.actuator.health;

import de.filiadata.infrastructure.actuator.health.endpoint.AliveHealthEndpoint;
import de.filiadata.infrastructure.actuator.health.mvcendpoint.AliveHealthController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExtendedHealthMvcEndpointTest {

    private AliveHealthEndpoint extendedHealthEndpoint;
    private AliveHealthController extendedHealthMvcEndpoint;

    @Before
    public void init() {
        this.extendedHealthEndpoint = mock(AliveHealthEndpoint.class);
        this.extendedHealthMvcEndpoint = new AliveHealthController(this.extendedHealthEndpoint);
    }

    @Test
    public void up() {

        when(extendedHealthEndpoint.invoke()).thenReturn(new Health.Builder().up().build());

        ResponseEntity<Health> result = this.extendedHealthMvcEndpoint.health();

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody().getStatus(), is(Status.UP));
    }

    @Test
    public void upWithExceptionHandling() {

        when(extendedHealthEndpoint.invoke()).thenReturn(new Health.Builder().up().build());

        ResponseEntity<Health> result = this.extendedHealthMvcEndpoint.health();

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody().getStatus(), is(Status.UP));
    }

    @Test
    public void down() {

        when(extendedHealthEndpoint.invoke()).thenReturn(new Health.Builder().down().build());

        ResponseEntity<Health> result = this.extendedHealthMvcEndpoint.health();

        assertThat(result.getStatusCode(), is(HttpStatus.SERVICE_UNAVAILABLE));
        assertThat(result.getBody().getStatus(), is(Status.DOWN));
    }


}
