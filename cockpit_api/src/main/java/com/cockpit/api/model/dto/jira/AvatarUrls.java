package com.cockpit.api.model.dto.jira;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "48x48",
})
public class AvatarUrls {

    @JsonProperty("48x48")
    private String bigAvatar;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
    @JsonProperty("48x48")
    public String getBigAvatar() {
        return bigAvatar;
    }

    @JsonProperty("48x48")
    public void setBigAvatar(String bigAvatar) {
        this.bigAvatar = bigAvatar;
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
