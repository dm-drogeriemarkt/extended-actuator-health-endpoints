package de.filiadata.infrastructure.actuator.health;

import de.filiadata.infrastructure.actuator.health.endpoint.AliveHealthEndpoint;
import de.filiadata.infrastructure.actuator.health.endpoint.BasicHealthEndpoint;
import de.filiadata.infrastructure.actuator.health.endpoint.DetailHealthEndpoint;
import de.filiadata.infrastructure.actuator.health.indicator.ApplicationAliveIndicator;
import de.filiadata.infrastructure.actuator.health.indicator.BasicHealthIndicator;
import de.filiadata.infrastructure.actuator.health.indicator.DetailHealthIndicator;
import de.filiadata.infrastructure.actuator.health.mvcendpoint.AliveHealthController;
import de.filiadata.infrastructure.actuator.health.mvcendpoint.BasicHealthController;
import de.filiadata.infrastructure.actuator.health.mvcendpoint.DetailHealthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.HealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.OrderedHealthAggregator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * ExtendedHealthEndpointAutoConfiguration configures HealthIndicators like Spring Boots HealthEndpoint, but with different levels of HealthIndicators, currently:
 *
 * * {@link ApplicationAliveIndicator} (faster than basic, used by load balancer to decide if application is alive)
 * * {@link BasicHealthIndicator} (only basic, combined should not take longer than 5 seconds)
 * * {@link DetailHealthIndicator} (includes all, combined may take up to 30 seconds)
 *
 * Beans implementing one of these Interfaces are automatically included in one of these health endpoint categories.
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(HealthIndicatorAutoConfiguration.class)
@EnableConfigurationProperties({ExtendedHealthProperties.class, ManagementServerProperties.class})
public class ExtendedHealthEndpointAutoConfiguration {

    @Autowired
    private ExtendedHealthProperties properties;


    @Bean
    @ConditionalOnMissingBean(AliveHealthEndpoint.class)
    public AliveHealthEndpoint applicationAliveEndpoint() {

        return new AliveHealthEndpoint(properties.getAliveId(), new OrderedHealthAggregator());
    }

    @Bean
    @ConditionalOnMissingBean(BasicHealthEndpoint.class)
    public BasicHealthEndpoint basicHealthEndpoint() {

        return new BasicHealthEndpoint(properties.getBasicId(), new OrderedHealthAggregator());
    }

    @Bean
    @ConditionalOnMissingBean(DetailHealthEndpoint.class)
    public DetailHealthEndpoint detailHealthEndpoint() {

        return new DetailHealthEndpoint(properties.getDetailId(), new OrderedHealthAggregator());
    }

    @Bean
    @ConditionalOnMissingBean(AliveHealthController.class)
    public AliveHealthController aliveMvcEndpoint() {
        return new AliveHealthController(applicationAliveEndpoint());
    }

    @Bean
    @ConditionalOnMissingBean(BasicHealthController.class)
    public BasicHealthController basicMvcEndpoint() {
        return new BasicHealthController(basicHealthEndpoint());
    }

    @Bean
    @ConditionalOnMissingBean(DetailHealthController.class)
    public DetailHealthController detailMvcEndpoint() {
        return new DetailHealthController(detailHealthEndpoint());
    }

    @Bean
    @ConditionalOnMissingBean(ApplicationAliveIndicator.class)
    public ApplicationAliveIndicator defaultApplicationAliveIndicator(ApplicationContext applicationContext) {
        return new DefaultApplicationAliveIndicator(applicationContext);
    }

    public static class DefaultApplicationAliveIndicator implements ApplicationAliveIndicator {

        private ApplicationContext applicationContext;

        @Autowired
        public DefaultApplicationAliveIndicator(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        @Override
        public Health health() {

            Health.Builder healthBuilder = Health.down();

            if (applicationContext != null && applicationContext.getId() != null) {
                healthBuilder.up();
            }

            return healthBuilder.build();
        }
    }
}
