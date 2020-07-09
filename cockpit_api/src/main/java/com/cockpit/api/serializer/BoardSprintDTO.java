package com.cockpit.api.serializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardSprintDTO {
    private Long id;

    public Long getId() {
        return id;
    }
}
