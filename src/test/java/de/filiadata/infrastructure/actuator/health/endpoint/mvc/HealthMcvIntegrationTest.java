package de.filiadata.infrastructure.actuator.health.endpoint.mvc;

import de.filiadata.infrastructure.actuator.health.endpoint.AliveHealthEndpoint;
import de.filiadata.infrastructure.actuator.health.endpoint.BasicHealthEndpoint;
import de.filiadata.infrastructure.actuator.health.endpoint.DetailHealthEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class HealthMcvIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private AliveHealthEndpoint aliveHealthEndpoint;
    @SpyBean
    private BasicHealthEndpoint basicHealthEndpoint;
    @SpyBean
    private DetailHealthEndpoint detailHealthEndpoint;


    @Test
    public void up() throws Exception {

        when(aliveHealthEndpoint.invoke()).thenReturn(Health.up().build());

        mockMvc.perform(get("/health/alive"))
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void down() throws Exception {

        when(aliveHealthEndpoint.invoke()).thenReturn(Health.down().build());

        mockMvc.perform(get("/health/alive"))
                .andExpect(status().isServiceUnavailable())
        ;
    }

    @Test
    public void unknown() throws Exception {

        when(aliveHealthEndpoint.invoke()).thenReturn(Health.unknown().build());

        mockMvc.perform(get("/health/alive"))
                .andExpect(status().isServiceUnavailable())
        ;
    }

    @Test
    public void outOfService() throws Exception {

        when(aliveHealthEndpoint.invoke()).thenReturn(Health.outOfService().build());

        mockMvc.perform(get("/health/alive"))
                .andExpect(status().isServiceUnavailable())
        ;
    }

    @Test
    public void basic() throws Exception {

        when(basicHealthEndpoint.invoke()).thenReturn(Health.up().build());

        mockMvc.perform(get("/health/basic"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":\"UP\"}"))
        ;
    }

    @Test
    public void detail() throws Exception {

        when(detailHealthEndpoint.invoke()).thenReturn(Health.up().build());

        mockMvc.perform(get("/health/detail"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":\"UP\"}"))
        ;
    }

}
