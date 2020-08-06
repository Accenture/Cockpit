package com.cockpit.api.model.dto.jira;

import com.fasterxml.jackson.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "maxResults",
    "startAt",
    "isLast",
    "values"
})
public class SprintHeaders {

    @JsonProperty("maxResults")
    private Integer maxResults;
    @JsonProperty("startAt")
    private Integer startAt;
    @JsonProperty("isLast")
    private Boolean isLast;
    @JsonProperty("values")
    private List<SprintJira> values = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("maxResults")
    public Integer getMaxResults() {
        return maxResults;
    }

    @JsonProperty("maxResults")
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    @JsonProperty("startAt")
    public Integer getStartAt() {
        return startAt;
    }

    @JsonProperty("startAt")
    public void setStartAt(Integer startAt) {
        this.startAt = startAt;
    }

    @JsonProperty("isLast")
    public Boolean getIsLast() {
        return isLast;
    }

    @JsonProperty("isLast")
    public void setIsLast(Boolean isLast) {
        this.isLast = isLast;
    }

    @JsonProperty("values")
    public List<SprintJira> getValues() {
        return values;
    }

    @JsonProperty("values")
    public void setValues(List<SprintJira> values) {
        this.values = values;
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
