package com.cockpit.api.model.dto.jira;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "completedIssues",
        "issuesNotCompletedInCurrentSprint",
        "puntedIssues"
})
public class SprintReportContent {

    @JsonProperty("completedIssues")
    private List<SprintReportIssue> completedIssues;

    @JsonProperty("issuesNotCompletedInCurrentSprint")
    private List<SprintReportIssue> issuesNotCompletedInCurrentSprint;

    @JsonProperty("puntedIssues")
    private List<SprintReportIssue> puntedIssues;

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

    @JsonProperty("completedIssues")
    public List<SprintReportIssue> getCompletedIssues() {
        return completedIssues;
    }

    @JsonProperty("completedIssues")
    public void setCompletedIssues(List<SprintReportIssue> completedIssues) {
        this.completedIssues = completedIssues;
    }

    @JsonProperty("puntedIssues")
    public List<SprintReportIssue> getPuntedIssues() {
        return puntedIssues;
    }

    @JsonProperty("puntedIssues")
    public void setPuntedIssues(List<SprintReportIssue> puntedIssues) {
        this.puntedIssues = puntedIssues;
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

