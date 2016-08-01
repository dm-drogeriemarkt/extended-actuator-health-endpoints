package de.filiadata.infrastructure.actuator.health.mvcendpoint;

import de.filiadata.infrastructure.actuator.health.endpoint.DetailHealthEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetailHealthController {

    private DetailHealthEndpoint extendedHealthEndpoint;

    @Autowired
    public DetailHealthController(DetailHealthEndpoint extendedHealthEndpoint) {
        this.extendedHealthEndpoint = extendedHealthEndpoint;
    }

    @RequestMapping("${management.context-path:}/${extended.health.detailId:health/detail}")
    public ResponseEntity<Health> health() {
        Health health = this.extendedHealthEndpoint.invoke();
        return new ResponseEntity<>(health, HttpStatus.OK);
    }
}
