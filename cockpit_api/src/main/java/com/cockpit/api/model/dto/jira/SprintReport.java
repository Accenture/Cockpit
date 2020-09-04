package com.cockpit.api.model.dto.jira;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "contents"
})
public class SprintReport {

    @JsonProperty("contents")
    private SprintReportContent contents;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("contents")
    public SprintReportContent getContents() {
        return contents;
    }

    @JsonProperty("contents")
    public void setContents(SprintReportContent contents) {
        this.contents = contents;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

}
