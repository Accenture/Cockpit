package com.cockpit.api.model.dto.jira;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "48x48",
        "24x24",
        "16x16",
        "32x32",
})
public class AvatarUrls {

    @JsonProperty("48x48")
    private String bigAvatar;
    @JsonProperty("24x24")
    private String smallAvatar;
    @JsonProperty("16x16")
    private String verySmallAvatar;
    @JsonProperty("32x32")
    private String mediumAvatar;

    @JsonProperty("48x48")
    public String getBigAvatar() {
        return bigAvatar;
    }

    @JsonProperty("48x48")
    public void setBigAvatar(String bigAvatar) {
        this.bigAvatar = bigAvatar;
    }

    @JsonProperty("24x24")
    public String getSmallAvatar() {
        return smallAvatar;
    }

    @JsonProperty("24x24")
    public void setSmallAvatar(String smallAvatar) {
        this.smallAvatar = smallAvatar;
    }

    @JsonProperty("16x16")
    public String getVerySmallAvatar() {
        return verySmallAvatar;
    }

    @JsonProperty("16x16")
    public void setVerySmallAvatar(String verySmallAvatar) {
        this.verySmallAvatar = verySmallAvatar;
    }

    @JsonProperty("32x32")
    public String getMediumAvatar() {
        return mediumAvatar;
    }

    @JsonProperty("32x32")
    public void setMediumAvatar(String mediumAvatar) {
        this.mediumAvatar = mediumAvatar;
    }
}
