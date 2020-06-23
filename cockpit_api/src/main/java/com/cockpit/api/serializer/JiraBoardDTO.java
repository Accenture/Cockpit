package com.cockpit.api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraBoardDTO {
    private String name;
    private Integer id;
    private String type;
    private JiraBoardProjectDTO location;

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public JiraBoardProjectDTO getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(JiraBoardProjectDTO location) {
        this.location = location;
    }
}
