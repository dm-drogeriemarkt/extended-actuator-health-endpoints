package de.filiadata.infrastructure.actuator.health;

import de.filiadata.infrastructure.actuator.health.endpoint.AliveHealthEndpoint;
import de.filiadata.infrastructure.actuator.health.endpoint.BasicHealthEndpoint;
import de.filiadata.infrastructure.actuator.health.endpoint.DetailHealthEndpoint;
import de.filiadata.infrastructure.actuator.health.endpoint.mvc.ExtendedHealthMvcEndpoint;
import de.filiadata.infrastructure.actuator.health.indicator.ApplicationAliveIndicator;
import de.filiadata.infrastructure.actuator.health.indicator.BasicHealthIndicator;
import de.filiadata.infrastructure.actuator.health.indicator.DetailHealthIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.HealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.OrderedHealthAggregator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * ExtendedHealthEndpointAutoConfiguration configures HealthIndicators like Spring Boots HealthEndpoint, but with different levels of HealthIndicators, currently:
 * <p>
 * * {@link ApplicationAliveIndicator} (faster than basic, used by load balancer to decide if application is alive)
 * * {@link BasicHealthIndicator} (only basic, combined should not take longer than 5 seconds)
 * * {@link DetailHealthIndicator} (includes all, combined may take up to 30 seconds)
 * <p>
 * Beans implementing one of these Interfaces are automatically included in one of these health endpoint categories.
 */
@ManagementContextConfiguration
@AutoConfigureAfter(HealthIndicatorAutoConfiguration.class)
public class ExtendedHealthEndpointAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(AliveHealthEndpoint.class)
    @ConfigurationProperties("extended.health.alive")
    public AliveHealthEndpoint applicationAliveEndpoint() {

        return new AliveHealthEndpoint("alive", new OrderedHealthAggregator());
    }

    @Bean
    @ConditionalOnMissingBean(BasicHealthEndpoint.class)
    @ConfigurationProperties("extended.health.basic")
    public BasicHealthEndpoint basicHealthEndpoint() {

        return new BasicHealthEndpoint("basic", new OrderedHealthAggregator());
    }

    @Bean
    @ConditionalOnMissingBean(DetailHealthEndpoint.class)
    @ConfigurationProperties("extended.health.detail")
    public DetailHealthEndpoint detailHealthEndpoint() {

        return new DetailHealthEndpoint("detail", new OrderedHealthAggregator());
    }

    @Bean
    public ExtendedHealthMvcEndpoint aliveMvcEndpoint() {
        return new ExtendedHealthMvcEndpoint(applicationAliveEndpoint());
    }

    @Bean
    public ExtendedHealthMvcEndpoint basicMvcEndpoint() {
        return new ExtendedHealthMvcEndpoint(basicHealthEndpoint());
    }

    @Bean
    public ExtendedHealthMvcEndpoint detailMvcEndpoint() {
        return new ExtendedHealthMvcEndpoint(detailHealthEndpoint());
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
