package de.filiadata.infrastructure.actuator.health.mvcendpoint;

import de.filiadata.infrastructure.actuator.health.endpoint.DetailHealthEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetailHealthController extends AbstractExtendedHealthMvcEndpoint<DetailHealthEndpoint> {

    public DetailHealthController(DetailHealthEndpoint extendedHealthEndpoint) {
        super(extendedHealthEndpoint);
    }

    @RequestMapping("/${extended.health.detailId:health/detail}")
    public ResponseEntity<Health> health() {
        Health health;
        try {
            health = this.extendedHealthEndpoint.invoke();
        } catch (Exception e) {
            health = Health.down(e).build();
        }

        HttpStatus status = this.statusMapping.get(health.getStatus().getCode());
        return new ResponseEntity<>(health, status);
    }

}
