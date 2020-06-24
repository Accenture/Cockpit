package com.cockpit.api.serializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraBoardProjectDTO {
    private String projectKey;
    private String displayName;
    private String projectName;

    public String getProjectKey() {
        return projectKey;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
