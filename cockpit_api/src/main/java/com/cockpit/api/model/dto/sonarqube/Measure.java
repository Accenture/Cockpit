package com.cockpit.api.model.dto.sonarqube;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "metric",
        "value",
})
public class Measure {

    @JsonProperty("metric")
    private String metric;

    @JsonProperty("value")
    private String value;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("metric")
    public String getMetric() {
        return metric;
    }

    @JsonProperty("metric")
    public void setMetric(String metric) {
        this.metric = metric;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
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
