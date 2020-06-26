package com.cockpit.api.serializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardSprintDTO {
    private Long id;
    private Integer originBoardId;

    public Long getId() {
        return id;
    }

    public Integer getOriginBoardId() {
        return originBoardId;
    }
}
