package com.cockpit.api.model.dto.sonarqube;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "components",
})
public class ProjectListResponse {
    @JsonProperty("components")
    private List<ProjectComponent> components;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("components")
    public List<ProjectComponent> getComponents() {
        return components;
    }

    @JsonProperty("components")
    public void setComponents(List<ProjectComponent> components) {
        this.components = components;
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
