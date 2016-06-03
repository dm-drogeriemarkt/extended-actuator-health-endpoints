package de.filiadata.infrastructure.actuator.health.mvcendpoint;

import de.filiadata.infrastructure.actuator.health.endpoint.AliveHealthEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AliveHealthControllerTest {

    private MockMvc mockMvc;
    private AliveHealthEndpoint endpoint;

    @Before
    public void setUp() {
        this.endpoint = mock(AliveHealthEndpoint.class);
        AliveHealthController controller = new AliveHealthController(endpoint);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void up() throws Exception {

        when(endpoint.invoke()).thenReturn(Health.up().build());

        mockMvc.perform(get("/health/alive"))
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void down() throws Exception {

        when(endpoint.invoke()).thenReturn(Health.down().build());

        mockMvc.perform(get("/health/alive"))
                .andExpect(status().isServiceUnavailable())
        ;
    }

    @Test
    public void unknown() throws Exception {

        when(endpoint.invoke()).thenReturn(Health.unknown().build());

        mockMvc.perform(get("/health/alive"))
                .andExpect(status().isServiceUnavailable())
        ;
    }

    @Test
    public void outOfService() throws Exception {

        when(endpoint.invoke()).thenReturn(Health.outOfService().build());

        mockMvc.perform(get("/health/alive"))
                .andExpect(status().isServiceUnavailable())
        ;
    }
}
