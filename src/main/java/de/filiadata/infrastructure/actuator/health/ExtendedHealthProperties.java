package de.filiadata.infrastructure.actuator.health;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "extended.health")
public class ExtendedHealthProperties {

    private String aliveId = "health/alive";
    private String basicId = "health/basic";
    private String detailId = "health/detail";

    /**
     * Endpoint id (used as url path, relative to spring boot management path) for alive check
     *
     * @return aliveId used as endpoint URL
     */
    public String getAliveId() {
        return aliveId;
    }

    /**
     * Endpoint id (used as url path, relative to spring boot management path) for alive check
     *
     * @param aliveId aliveId used as endpoint URL
     */
    public void setAliveId(String aliveId) {
        this.aliveId = aliveId;
    }

    /**
     * Endpoint id (used as url path, relative to spring boot management path) for basic check
     *
     * @return basicId used as endpoint URL
     */
    public String getBasicId() {
        return basicId;
    }

    /**
     * Endpoint id (used as url path, relative to spring boot management path) for basic check
     *
     * @param basicId basicId used as endpoint URL
     */
    public void setBasicId(String basicId) {
        this.basicId = basicId;
    }

    /**
     * Endpoint id (used as url path, relative to spring boot management path) for detail check
     *
     * @return detailId used as endpoint URL
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * Endpoint id (used as url path, relative to spring boot management path) for detail check
     *
     * @param detailId detailId used as endpoint URL
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }
}
