package com.cockpit.api.model.dto.jira;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self",
        "value,",
        "id"
})
public class CustomField {
    @JsonProperty("self")
    private String self;
    @JsonProperty("value")
    private String value;
    @JsonProperty("id")
    private String id;


    @JsonProperty("self")
    public String getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(String self) {
        this.self = self;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("created")
    public void setId(String id) {
        this.id = id;
    }


}

