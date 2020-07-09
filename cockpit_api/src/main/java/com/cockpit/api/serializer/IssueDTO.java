package com.cockpit.api.serializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueDTO {
    private Integer id;
    private String key;

    public Integer getId() {
        return id;
    }

    public String getKey() {
        return key;
    }
}