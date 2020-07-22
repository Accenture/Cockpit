package com.cockpit.api.model.dto.jira;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "issuetype",
        "timespent",
        "customfield_10074",
        "customfield_10075",
        "project",
        "customfield_10076",
        "fixVersions",
        "customfield_10077",
        "aggregatetimespent",
        "customfield_10078",
        "resolution",
        "resolutiondate",
        "workratio",
        "lastViewed",
        "created",
        "priority",
        "labels",
        "timeestimate",
        "aggregatetimeoriginalestimate",
        "versions",
        "assignee",
        "updated",
        "status",
        "components",
        "customfield_10050",
        "timeoriginalestimate",
        "customfield_10051",
        "customfield_10052",
        "description",
        "customfield_10054",
        "customfield_10010",
        "customfield_10011",
        "customfield_10055",
        "customfield_10012",
        "customfield_10056",
        "customfield_10013",
        "customfield_10049",
        "security",
        "customfield_10008",
        "customfield_10009",
        "aggregatetimeestimate",
        "summary",
        "customfield_10081",
        "subtasks",
        "customfield_10026",
        "customfield_10000",
        "customfield_10001",
        "customfield_10046",
        "customfield_10002",
        "customfield_10047",
        "customfield_10003",
        "customfield_10048",
        "customfield_10004",
        "customfield_10020",
        "environment",
        "duedate",
})
public class Fields {

    @JsonProperty("issuetype")
    private IssueType issuetype;
    @JsonProperty("timespent")
    private Object timespent;
    @JsonProperty("customfield_10074")
    private CustomField customfield10074;
    @JsonProperty("customfield_10075")
    private CustomField customfield10075;
    @JsonProperty("project")
    private Project project;
    @JsonProperty("customfield_10076.id")
    private CustomField customfield10076;
    @JsonProperty("customfield_10077")
    private CustomField customfield10077;
    @JsonProperty("aggregatetimespent")
    private Object aggregatetimespent;
    @JsonProperty("customfield_10078")
    private String customfield10078;
    @JsonProperty("resolution")
    private Object resolution;
    @JsonProperty("resolutiondate")
    private Object resolutiondate;
    @JsonProperty("workratio")
    private Long workratio;
    @JsonProperty("lastViewed")
    private Object lastViewed;
    @JsonProperty("created")
    private String created;
    @JsonProperty("priority")
    private Priority priority;
    @JsonProperty("labels")
    private List<String> labels = null;
    @JsonProperty("timeestimate")
    private Object timeestimate;
    @JsonProperty("aggregatetimeoriginalestimate")
    private Object aggregatetimeoriginalestimate;
    @JsonProperty("versions")
    private List<Object> versions = null;
    @JsonProperty("assignee")
    private Object assignee;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("components")
    private List<Object> components = null;
    @JsonProperty("customfield_10050")
    private Object customfield10050;
    @JsonProperty("timeoriginalestimate")
    private Object timeoriginalestimate;
    @JsonProperty("customfield_10051")
    private Object customfield10051;
    @JsonProperty("customfield_10052")
    private Object customfield10052;
    @JsonProperty("description")
    private String description;
    @JsonProperty("customfield_10054")
    private Object customfield10054;
    @JsonProperty("customfield_10010")
    private List<String> customfield10010 = null;
    @JsonProperty("customfield_10011")
    private String customfield10011;
    @JsonProperty("customfield_10055")
    private Object customfield10055;
    @JsonProperty("customfield_10012")
    private Object customfield10012;
    @JsonProperty("customfield_10056")
    private Object customfield10056;
    @JsonProperty("customfield_10013")
    private Object customfield10013;
    @JsonProperty("customfield_10049")
    private Object customfield10049;
    @JsonProperty("security")
    private Object security;
    @JsonProperty("customfield_10008")
    private String customfield10008;
    @JsonProperty("customfield_10009")
    private Object customfield10009;
    @JsonProperty("aggregatetimeestimate")
    private Object aggregatetimeestimate;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("customfield_10081")
    private CustomField customfield10081;
    @JsonProperty("subtasks")
    private List<Object> subtasks = null;
    @JsonProperty("customfield_10026")
    private Integer customfield10026;
    @JsonProperty("customfield_10000")
    private String customfield10000;
    @JsonProperty("customfield_10001")
    private Object customfield10001;
    @JsonProperty("customfield_10046")
    private Object customfield10046;
    @JsonProperty("customfield_10002")
    private Object customfield10002;
    @JsonProperty("customfield_10047")
    private Object customfield10047;
    @JsonProperty("customfield_10003")
    private Object customfield10003;
    @JsonProperty("customfield_10048")
    private Object customfield10048;
    @JsonProperty("customfield_10004")
    private Object customfield10004;
    @JsonProperty("customfield_10020")
    private Object customfield10020;
    @JsonProperty("customfield_10071")
    private Object customfield10071;
    @JsonProperty("customfield_10072")
    private Object customfield10072;
    @JsonProperty("environment")
    private Object environment;
    @JsonProperty("duedate")
    private Object duedate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("issuetype")
    public IssueType getIssuetype() {
        return issuetype;
    }

    @JsonProperty("issuetype")
    public void setIssuetype(IssueType issuetype) {
        this.issuetype = issuetype;
    }

    @JsonProperty("timespent")
    public Object getTimespent() {
        return timespent;
    }

    @JsonProperty("timespent")
    public void setTimespent(Object timespent) {
        this.timespent = timespent;
    }

    @JsonProperty("customfield_10074")
    public CustomField getCustomfield10074() {
        return customfield10074;
    }

    @JsonProperty("customfield_10074")
    public void setCustomfield10074(CustomField customfield10074) {
        this.customfield10074 = customfield10074;
    }

    @JsonProperty("customfield_10075")
    public CustomField getCustomfield10075() {
        return customfield10075;
    }

    @JsonProperty("customfield_10075")
    public void setCustomfield10075(CustomField customfield10075) {
        this.customfield10075 = customfield10075;
    }

    @JsonProperty("project")
    public Project getProject() {
        return project;
    }

    @JsonProperty("project")
    public void setProject(Project project) {
        this.project = project;
    }

    @JsonProperty("customfield_10076")
    public CustomField getCustomfield10076() {
        return customfield10076;
    }

    @JsonProperty("customfield_10076")
    public void setCustomfield10076(CustomField customfield10076) {
        this.customfield10076 = customfield10076;
    }

    @JsonProperty("customfield_10077")
    public CustomField getCustomfield10077() {
        return customfield10077;
    }

    @JsonProperty("customfield_10077")
    public void setCustomfield10077(CustomField customfield10077) {
        this.customfield10077 = customfield10077;
    }

    @JsonProperty("aggregatetimespent")
    public Object getAggregatetimespent() {
        return aggregatetimespent;
    }

    @JsonProperty("aggregatetimespent")
    public void setAggregatetimespent(Object aggregatetimespent) {
        this.aggregatetimespent = aggregatetimespent;
    }

    @JsonProperty("customfield_10078")
    public String getCustomfield10078() {
        return customfield10078;
    }

    @JsonProperty("customfield_10078")
    public void setCustomfield10078(String customfield10078) {
        this.customfield10078 = customfield10078;
    }

    @JsonProperty("resolution")
    public Object getResolution() {
        return resolution;
    }

    @JsonProperty("resolution")
    public void setResolution(Object resolution) {
        this.resolution = resolution;
    }

    @JsonProperty("resolutiondate")
    public Object getResolutiondate() {
        return resolutiondate;
    }

    @JsonProperty("resolutiondate")
    public void setResolutiondate(Object resolutiondate) {
        this.resolutiondate = resolutiondate;
    }

    @JsonProperty("workratio")
    public Long getWorkratio() {
        return workratio;
    }

    @JsonProperty("workratio")
    public void setWorkratio(Long workratio) {
        this.workratio = workratio;
    }

    @JsonProperty("lastViewed")
    public Object getLastViewed() {
        return lastViewed;
    }

    @JsonProperty("lastViewed")
    public void setLastViewed(Object lastViewed) {
        this.lastViewed = lastViewed;
    }

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    @JsonProperty("priority")
    public Priority getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @JsonProperty("labels")
    public List<String> getLabels() {
        return labels;
    }

    @JsonProperty("labels")
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    @JsonProperty("timeestimate")
    public Object getTimeestimate() {
        return timeestimate;
    }

    @JsonProperty("timeestimate")
    public void setTimeestimate(Object timeestimate) {
        this.timeestimate = timeestimate;
    }

    @JsonProperty("aggregatetimeoriginalestimate")
    public Object getAggregatetimeoriginalestimate() {
        return aggregatetimeoriginalestimate;
    }

    @JsonProperty("aggregatetimeoriginalestimate")
    public void setAggregatetimeoriginalestimate(Object aggregatetimeoriginalestimate) {
        this.aggregatetimeoriginalestimate = aggregatetimeoriginalestimate;
    }

    @JsonProperty("versions")
    public List<Object> getVersions() {
        return versions;
    }

    @JsonProperty("versions")
    public void setVersions(List<Object> versions) {
        this.versions = versions;
    }

    @JsonProperty("assignee")
    public Object getAssignee() {
        return assignee;
    }

    @JsonProperty("assignee")
    public void setAssignee(Object assignee) {
        this.assignee = assignee;
    }

    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @JsonProperty("components")
    public List<Object> getComponents() {
        return components;
    }

    @JsonProperty("components")
    public void setComponents(List<Object> components) {
        this.components = components;
    }

    @JsonProperty("customfield_10050")
    public Object getCustomfield10050() {
        return customfield10050;
    }

    @JsonProperty("customfield_10050")
    public void setCustomfield10050(Object customfield10050) {
        this.customfield10050 = customfield10050;
    }

    @JsonProperty("timeoriginalestimate")
    public Object getTimeoriginalestimate() {
        return timeoriginalestimate;
    }

    @JsonProperty("timeoriginalestimate")
    public void setTimeoriginalestimate(Object timeoriginalestimate) {
        this.timeoriginalestimate = timeoriginalestimate;
    }

    @JsonProperty("customfield_10051")
    public Object getCustomfield10051() {
        return customfield10051;
    }

    @JsonProperty("customfield_10051")
    public void setCustomfield10051(Object customfield10051) {
        this.customfield10051 = customfield10051;
    }

    @JsonProperty("customfield_10052")
    public Object getCustomfield10052() {
        return customfield10052;
    }

    @JsonProperty("customfield_10052")
    public void setCustomfield10052(Object customfield10052) {
        this.customfield10052 = customfield10052;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("customfield_10054")
    public Object getCustomfield10054() {
        return customfield10054;
    }

    @JsonProperty("customfield_10054")
    public void setCustomfield10054(Object customfield10054) {
        this.customfield10054 = customfield10054;
    }

    @JsonProperty("customfield_10010")
    public List<String> getCustomfield10010() {
        return customfield10010;
    }

    @JsonProperty("customfield_10010")
    public void setCustomfield10010(List<String> customfield10010) {
        this.customfield10010 = customfield10010;
    }

    @JsonProperty("customfield_10011")
    public String getCustomfield10011() {
        return customfield10011;
    }

    @JsonProperty("customfield_10011")
    public void setCustomfield10011(String customfield10011) {
        this.customfield10011 = customfield10011;
    }

    @JsonProperty("customfield_10055")
    public Object getCustomfield10055() {
        return customfield10055;
    }

    @JsonProperty("customfield_10055")
    public void setCustomfield10055(Object customfield10055) {
        this.customfield10055 = customfield10055;
    }

    @JsonProperty("customfield_10012")
    public Object getCustomfield10012() {
        return customfield10012;
    }

    @JsonProperty("customfield_10012")
    public void setCustomfield10012(Object customfield10012) {
        this.customfield10012 = customfield10012;
    }

    @JsonProperty("customfield_10056")
    public Object getCustomfield10056() {
        return customfield10056;
    }

    @JsonProperty("customfield_10056")
    public void setCustomfield10056(Object customfield10056) {
        this.customfield10056 = customfield10056;
    }

    @JsonProperty("customfield_10013")
    public Object getCustomfield10013() {
        return customfield10013;
    }

    @JsonProperty("customfield_10013")
    public void setCustomfield10013(Object customfield10013) {
        this.customfield10013 = customfield10013;
    }

    @JsonProperty("customfield_10049")
    public Object getCustomfield10049() {
        return customfield10049;
    }

    @JsonProperty("customfield_10049")
    public void setCustomfield10049(Object customfield10049) {
        this.customfield10049 = customfield10049;
    }

    @JsonProperty("security")
    public Object getSecurity() {
        return security;
    }

    @JsonProperty("security")
    public void setSecurity(Object security) {
        this.security = security;
    }

    @JsonProperty("customfield_10008")
    public String getCustomfield10008() {
        return customfield10008;
    }

    @JsonProperty("customfield_10008")
    public void setCustomfield10008(String customfield10008) {
        this.customfield10008 = customfield10008;
    }

    @JsonProperty("customfield_10009")
    public Object getCustomfield10009() {
        return customfield10009;
    }

    @JsonProperty("customfield_10009")
    public void setCustomfield10009(Object customfield10009) {
        this.customfield10009 = customfield10009;
    }

    @JsonProperty("aggregatetimeestimate")
    public Object getAggregatetimeestimate() {
        return aggregatetimeestimate;
    }

    @JsonProperty("aggregatetimeestimate")
    public void setAggregatetimeestimate(Object aggregatetimeestimate) {
        this.aggregatetimeestimate = aggregatetimeestimate;
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("customfield_10081")
    public CustomField getCustomfield10081() {
        return customfield10081;
    }

    @JsonProperty("customfield_10081")
    public void setCustomfield10081(CustomField customfield10081) {
        this.customfield10081 = customfield10081;
    }

    @JsonProperty("subtasks")
    public List<Object> getSubtasks() {
        return subtasks;
    }

    @JsonProperty("subtasks")
    public void setSubtasks(List<Object> subtasks) {
        this.subtasks = subtasks;
    }

    @JsonProperty("customfield_10026")
    public Integer getCustomfield10026() {
        return customfield10026;
    }

    @JsonProperty("customfield_10026")
    public void setCustomfield10026(Integer customfield10026) {
        this.customfield10026 = customfield10026;
    }

    @JsonProperty("customfield_10000")
    public String getCustomfield10000() {
        return customfield10000;
    }

    @JsonProperty("customfield_10000")
    public void setCustomfield10000(String customfield10000) {
        this.customfield10000 = customfield10000;
    }

    @JsonProperty("customfield_10001")
    public Object getCustomfield10001() {
        return customfield10001;
    }

    @JsonProperty("customfield_10001")
    public void setCustomfield10001(Object customfield10001) {
        this.customfield10001 = customfield10001;
    }

    @JsonProperty("customfield_10046")
    public Object getCustomfield10046() {
        return customfield10046;
    }

    @JsonProperty("customfield_10046")
    public void setCustomfield10046(Object customfield10046) {
        this.customfield10046 = customfield10046;
    }

    @JsonProperty("customfield_10002")
    public Object getCustomfield10002() {
        return customfield10002;
    }

    @JsonProperty("customfield_10002")
    public void setCustomfield10002(Object customfield10002) {
        this.customfield10002 = customfield10002;
    }

    @JsonProperty("customfield_10047")
    public Object getCustomfield10047() {
        return customfield10047;
    }

    @JsonProperty("customfield_10047")
    public void setCustomfield10047(Object customfield10047) {
        this.customfield10047 = customfield10047;
    }

    @JsonProperty("customfield_10003")
    public Object getCustomfield10003() {
        return customfield10003;
    }

    @JsonProperty("customfield_10003")
    public void setCustomfield10003(Object customfield10003) {
        this.customfield10003 = customfield10003;
    }

    @JsonProperty("customfield_10048")
    public Object getCustomfield10048() {
        return customfield10048;
    }

    @JsonProperty("customfield_10048")
    public void setCustomfield10048(Object customfield10048) {
        this.customfield10048 = customfield10048;
    }

    @JsonProperty("customfield_10004")
    public Object getCustomfield10004() {
        return customfield10004;
    }

    @JsonProperty("customfield_10004")
    public void setCustomfield10004(Object customfield10004) {
        this.customfield10004 = customfield10004;
    }

    @JsonProperty("customfield_10020")
    public Object getCustomfield10020() {
        return customfield10020;
    }

    @JsonProperty("customfield_10020")
    public void setCustomfield10020(Object customfield10020) {
        this.customfield10020 = customfield10020;
    }

    @JsonProperty("customfield_10071")
    public Object getCustomfield10071() {
        return customfield10071;
    }

    @JsonProperty("customfield_10071")
    public void setCustomfield10071(Object customfield10071) {
        this.customfield10071 = customfield10071;
    }

    @JsonProperty("customfield_10072")
    public Object getCustomfield10072() {
        return customfield10072;
    }

    @JsonProperty("customfield_10072")
    public void setCustomfield10072(Object customfield10072) {
        this.customfield10072 = customfield10072;
    }

    @JsonProperty
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("environment")
    public Object getEnvironment() {
        return environment;
    }

    @JsonProperty("environment")
    public void setEnvironment(Object environment) {
        this.environment = environment;
    }

    @JsonProperty("duedate")
    public Object getDuedate() {
        return duedate;
    }

    @JsonProperty("duedate")
    public void setDuedate(Object duedate) {
        this.duedate = duedate;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Status status) {
        this.status = status;
    }
}

