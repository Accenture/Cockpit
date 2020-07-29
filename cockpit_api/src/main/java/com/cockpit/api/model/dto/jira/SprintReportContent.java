package com.cockpit.api.model.dto.jira;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "issuesNotCompletedInCurrentSprint"
})
public class SprintReportContent {

    @JsonProperty("issuesNotCompletedInCurrentSprint")
    private List<SprintReportIssue> issuesNotCompletedInCurrentSprint;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("issuesNotCompletedInCurrentSprint")
    public List<SprintReportIssue> getIssuesNotCompletedInCurrentSprint() {
        return issuesNotCompletedInCurrentSprint;
    }

    @JsonProperty("issuesNotCompletedInCurrentSprint")
    public void setIssuesNotCompletedInCurrentSprint(List<SprintReportIssue> issuesNotCompletedInCurrentSprint) {
        this.issuesNotCompletedInCurrentSprint = issuesNotCompletedInCurrentSprint;
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

