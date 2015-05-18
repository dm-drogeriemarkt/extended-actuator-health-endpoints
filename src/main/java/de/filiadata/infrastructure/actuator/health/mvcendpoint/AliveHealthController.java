package de.filiadata.infrastructure.actuator.health.mvcendpoint;

import de.filiadata.infrastructure.actuator.health.endpoint.AliveHealthEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AliveHealthController {

    protected Map<String, HttpStatus> statusMapping = new HashMap<>();

    private AliveHealthEndpoint extendedHealthEndpoint;

    @Autowired
    public AliveHealthController(AliveHealthEndpoint extendedHealthEndpoint) {
        this.extendedHealthEndpoint = extendedHealthEndpoint;
        setupDefaultStatusMapping();
    }

    @RequestMapping("/${extended.health.aliveId:health/alive}")
    public ResponseEntity<Health> health() {
        Health health = this.extendedHealthEndpoint.invoke();
        HttpStatus status = this.statusMapping.get(health.getStatus().getCode());
        return new ResponseEntity<>(health, status);
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
