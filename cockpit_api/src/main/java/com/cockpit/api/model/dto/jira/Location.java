package com.cockpit.api.model.dto.jira;

import com.fasterxml.jackson.annotation.*;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "projectId",
    "name",
    "projectTypeKey",
    "avatarURI"
})
public class Location {

    @JsonProperty("projectId")
    private Integer projectId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("projectTypeKey")
    private String projectTypeKey;
    @JsonProperty("avatarURI")
    private String avatarURI;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("projectId")
    public Integer getProjectId() {
        return projectId;
    }

    @JsonProperty("projectId")
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("projectTypeKey")
    public String getProjectTypeKey() {
        return projectTypeKey;
    }

    @JsonProperty("projectTypeKey")
    public void setProjectTypeKey(String projectTypeKey) {
        this.projectTypeKey = projectTypeKey;
    }

    @JsonProperty("avatarURI")
    public String getAvatarURI() {
        return avatarURI;
    }

    @JsonProperty("avatarURI")
    public void setAvatarURI(String avatarURI) {
        this.avatarURI = avatarURI;
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

