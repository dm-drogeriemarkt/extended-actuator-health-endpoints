package de.dm.infrastructure.actuator.health.endpoint.mvc;

import de.dm.infrastructure.actuator.health.endpoint.ExtendedHealthEndpoint;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.AbstractEndpointMvcAdapter;
import org.springframework.boot.actuate.endpoint.mvc.ActuatorMediaTypes;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ExtendedHealthMvcEndpoint extends AbstractEndpointMvcAdapter<ExtendedHealthEndpoint<? extends HealthIndicator>> {

    private Map<String, HttpStatus> statusMapping = new HashMap<String, HttpStatus>();

    /**
     * Create a new {@link EndpointMvcAdapter}.
     *
     * @param delegate the underlying {@link Endpoint} to adapt.
     */
    public ExtendedHealthMvcEndpoint(ExtendedHealthEndpoint<? extends HealthIndicator> delegate) {
        super(delegate);
        setupDefaultStatusMapping();
    }

    @RequestMapping(method = RequestMethod.GET, produces = {
            ActuatorMediaTypes.APPLICATION_ACTUATOR_V1_JSON_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Object invoke(HttpServletRequest request) {
        if (!getDelegate().isEnabled()) {
            // Shouldn't happen because the request mapping should not be registered
            return getDisabledResponse();
        }
        Health health = getDelegate().invoke();
        HttpStatus status = this.statusMapping.get(health.getStatus().getCode());
        if (status != null) {
            return new ResponseEntity<>(health, status);
        }
        return health;
    }

    @Override
    public String getPath() {
        return "/health/" + getDelegate().getId();
    }

    private void setupDefaultStatusMapping() {
        addStatusMapping(Status.UP.getCode(), HttpStatus.OK);
        addStatusMapping(Status.DOWN.getCode(), HttpStatus.SERVICE_UNAVAILABLE);
        addStatusMapping(Status.UNKNOWN.getCode(), HttpStatus.SERVICE_UNAVAILABLE);
        addStatusMapping(Status.OUT_OF_SERVICE.getCode(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Add a status mapping to the existing set.
     *
     * @param statusCode the status code to map
     * @param httpStatus the http status
     */
    public void addStatusMapping(String statusCode, HttpStatus httpStatus) {
        Assert.notNull(statusCode, "StatusCode must not be null");
        Assert.notNull(httpStatus, "HttpStatus must not be null");
        this.statusMapping.put(statusCode, httpStatus);
    }
}
