package com.cockpit.api.model.dto.jira;

import com.fasterxml.jackson.annotation.*;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "self",
    "state",
    "name",
    "startDate",
    "endDate",
    "completeDate",
    "originBoardId",
    "goal"
})
public class SprintJira {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("self")
    private String self;
    @JsonProperty("state")
    private String state;
    @JsonProperty("name")
    private String name;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("completeDate")
    private String completeDate;
    @JsonProperty("originBoardId")
    private Integer originBoardId;
    @JsonProperty("goal")
    private String goal;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("self")
    public String getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(String self) {
        this.self = self;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("startDate")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("completeDate")
    public String getCompleteDate() {
        return completeDate;
    }

    @JsonProperty("completeDate")
    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    @JsonProperty("originBoardId")
    public Integer getOriginBoardId() {
        return originBoardId;
    }

    @JsonProperty("originBoardId")
    public void setOriginBoardId(Integer originBoardId) {
        this.originBoardId = originBoardId;
    }

    @JsonProperty("goal")
    public String getGoal() {
        return goal;
    }

    @JsonProperty("goal")
    public void setGoal(String goal) {
        this.goal = goal;
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
