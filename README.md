ExtendedHealthEndpointAutoConfiguration configures HealthIndicators like Spring Boots HealthEndpoint, but with different levels of HealthIndicators, currently:

 * ApplicationAliveIndicator (faster than basic, used by load balancer to decide if application is alive)
 * BasicHealthIndicator (only basic, combined should not take longer than 5 seconds)
 * DetailHealthIndicator (includes all, combined may take up to 30 seconds)

Beans implementing one of these Interfaces are automatically included in one of these health endpoint categories.
