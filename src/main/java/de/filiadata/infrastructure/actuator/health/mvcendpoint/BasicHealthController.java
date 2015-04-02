package de.filiadata.infrastructure.actuator.health.mvcendpoint;

import de.filiadata.infrastructure.actuator.health.endpoint.BasicHealthEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicHealthController extends AbstractExtendedHealthMvcEndpoint<BasicHealthEndpoint> {

    public BasicHealthController(BasicHealthEndpoint extendedHealthEndpoint) {
        super(extendedHealthEndpoint);
    }

    @RequestMapping("/${extended.health.basicId:health/basic}")
    public ResponseEntity<Health> health() {
        Health health = this.extendedHealthEndpoint.invoke();
        HttpStatus status = this.statusMapping.get(health.getStatus().getCode());
        return new ResponseEntity<>(health, status);
    }

}
