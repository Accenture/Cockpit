package cockpit.cockpitcore.domaine.jira;

import com.fasterxml.jackson.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self",
        "name",
        "id",
        "description",
        "actors"
})
public class JiraRole {

    @JsonProperty("self")
    private String self;
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("description")
    private String description;
    @JsonProperty("actors")
    private List<Actor> actors = null;
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

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("actors")
    public List<Actor> getActors() {
        return actors;
    }

    @JsonProperty("actors")
    public void setActors(List<Actor> actors) {
        this.actors = actors;
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
