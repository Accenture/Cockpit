
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
public class Fields_ {

    @JsonProperty("summary")
    private String summary;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("priority")
    private Priority_ priority;
    @JsonProperty("issuetype")
    private Issuetype_ issuetype;
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
    public Status getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonProperty("priority")
    public Priority_ getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(Priority_ priority) {
        this.priority = priority;
    }

    @JsonProperty("issuetype")
    public Issuetype_ getIssuetype() {
        return issuetype;
    }

    @JsonProperty("issuetype")
    public void setIssuetype(Issuetype_ issuetype) {
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
