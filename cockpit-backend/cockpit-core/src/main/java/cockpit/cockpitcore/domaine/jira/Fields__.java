
package cockpit.cockpitcore.domaine.jira;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "summary",
    "status",
    "priority",
    "issuetype"
})
public class Fields__ {

    @JsonProperty("summary")
    private String summary;
    @JsonProperty("status")
    private Status_ status;
    @JsonProperty("priority")
    private Priority__ priority;
    @JsonProperty("issuetype")
    private Issuetype__ issuetype;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("status")
    public Status_ getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Status_ status) {
        this.status = status;
    }

    @JsonProperty("priority")
    public Priority__ getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(Priority__ priority) {
        this.priority = priority;
    }

    @JsonProperty("issuetype")
    public Issuetype__ getIssuetype() {
        return issuetype;
    }

    @JsonProperty("issuetype")
    public void setIssuetype(Issuetype__ issuetype) {
        this.issuetype = issuetype;
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
