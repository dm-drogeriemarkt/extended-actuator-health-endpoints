package de.filiadata.infrastructure.actuator.health.mvcendpoint;

import de.filiadata.infrastructure.actuator.health.endpoint.ExtendedHealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractExtendedHealthMvcEndpoint<T extends ExtendedHealthEndpoint> {

    protected final T extendedHealthEndpoint;

    protected Map<String, HttpStatus> statusMapping = new HashMap<>();

    public AbstractExtendedHealthMvcEndpoint(T extendedHealthEndpoint) {
        this.extendedHealthEndpoint = extendedHealthEndpoint;
        setupDefaultStatusMapping();
    }

    private void setupDefaultStatusMapping() {
        addStatusMapping(Status.UP.getCode(), HttpStatus.OK);
        addStatusMapping(Status.DOWN.getCode(), HttpStatus.SERVICE_UNAVAILABLE);
        addStatusMapping(Status.UNKNOWN.getCode(), HttpStatus.SERVICE_UNAVAILABLE);
        addStatusMapping(Status.OUT_OF_SERVICE.getCode(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    public void addStatusMapping(String statusCode, HttpStatus httpStatus) {
        Assert.notNull(statusCode, "StatusCode must not be null");
        Assert.notNull(httpStatus, "HttpStatus must not be null");
        this.statusMapping.put(statusCode, httpStatus);
    }

}
