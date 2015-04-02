package de.filiadata.infrastructure.actuator.health;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "extended.health")
public class ExtendedHealthProperties {

    private String aliveId;
    private String basicId;
    private String detailId;

    /**
     * Endpoint id (used as url path, relative to spring boot management path) for alive check
     *
     * @return
     */
    public String getAliveId() {
        return aliveId;
    }

    /**
     * Endpoint id (used as url path, relative to spring boot management path) for alive check
     *
     * @param aliveId
     */
    public void setAliveId(String aliveId) {
        this.aliveId = aliveId;
    }

    /**
     * Endpoint id (used as url path, relative to spring boot management path) for basic check
     *
     * @return
     */
    public String getBasicId() {
        return basicId;
    }

    /**
     * Endpoint id (used as url path, relative to spring boot management path) for basic check
     *
     * @param basicId
     */
    public void setBasicId(String basicId) {
        this.basicId = basicId;
    }

    /**
     * Endpoint id (used as url path, relative to spring boot management path) for detail check
     *
     * @return
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * Endpoint id (used as url path, relative to spring boot management path) for detail check
     *
     * @param detailId
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }
}
