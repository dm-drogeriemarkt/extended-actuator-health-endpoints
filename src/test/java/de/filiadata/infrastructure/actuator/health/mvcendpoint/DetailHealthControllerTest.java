package de.filiadata.infrastructure.actuator.health.mvcendpoint;

import de.filiadata.infrastructure.actuator.health.endpoint.DetailHealthEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DetailHealthControllerTest {

    private MockMvc mockMvc;
    private DetailHealthEndpoint endpoint;

    @Before
    public void setUp() {
        this.endpoint = mock(DetailHealthEndpoint.class);
        DetailHealthController controller = new DetailHealthController(endpoint);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void detail() throws Exception {

        when(endpoint.invoke()).thenReturn(Health.up().build());

        mockMvc.perform(get("/health/detail"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":\"UP\"}"))
        ;
    }

}
