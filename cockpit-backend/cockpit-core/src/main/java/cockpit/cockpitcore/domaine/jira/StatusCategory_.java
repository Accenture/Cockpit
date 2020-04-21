
package cockpit.cockpitcore.domaine.jira;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "self",
    "id",
    "key",
    "colorName",
    "name"
})
public class StatusCategory_ {

    @JsonProperty("self")
    private String self;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("key")
    private String key;
    @JsonProperty("colorName")
    private String colorName;
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("self")
    public String getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(String self) {
        this.self = self;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("colorName")
    public String getColorName() {
        return colorName;
    }

    @JsonProperty("colorName")
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
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
