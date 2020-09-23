package com.cockpit.api.model.dto.sonarqube;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "component",
})
public class MetricResponse {
    @JsonProperty("component")
    private MetricComponent component;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("component")
    public MetricComponent getComponent() {
        return component;
    }

    @JsonProperty("component")
    public void setComponent(MetricComponent component) {
        this.component = component;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
