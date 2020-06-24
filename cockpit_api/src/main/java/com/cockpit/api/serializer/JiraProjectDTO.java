package com.cockpit.api.serializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraProjectDTO {
    private String name;
    private Integer id;
    private String key;

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
