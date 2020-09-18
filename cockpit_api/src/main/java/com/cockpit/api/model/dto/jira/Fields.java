package com.cockpit.api.model.dto.jira;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "issuetype",
        "project",
        "created",
        "priority",
        "status",
        "description",
        "summary",
        "customfield_10026",
})
public class Fields {

    @JsonProperty("issuetype")
    private IssueType issuetype;
    @JsonProperty("project")
    private Project project;
    @JsonProperty("created")
    private String created;
    @JsonProperty("priority")
    private Priority priority;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("description")
    private String description;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("customfield_10026")
    private Integer customfield10026;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("project")
    public Project getProject() {
        return project;
    }

    @JsonProperty("project")
    public void setProject(Project project) {
        this.project = project;
    }
    @JsonProperty("created")
    public String getCreated() {
        return created;
    }
    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }
    @JsonProperty("priority")
    public Priority getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }
    @JsonProperty("customfield_10026")
    public Integer getCustomfield10026() {
        return customfield10026;
    }
    @JsonProperty("customfield_10026")
    public void setCustomfield10026(Integer customfield10026) {
        this.customfield10026 = customfield10026;
    }

    @JsonProperty
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Status status) {
        this.status = status;
    }
}

