package com.cockpit.api.serializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardsDTO {
    private Long id;

    public Long getId() {
        return id;
    }
}
