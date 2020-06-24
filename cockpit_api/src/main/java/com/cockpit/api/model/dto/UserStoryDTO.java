package com.cockpit.api.model.dto;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import java.util.Date;

public class UserStoryDTO {

    private Long id;

    private Date creationDate;

    private Date startDate;

    private Date doneDate;

    private Double storyPoint;

    private String description;

    private String issueKey;

    private int jiraIssueId;

    private String priority;

    private String status;

    private String summary;

    private Sprint sprint;

    private Jira jira;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public Double getStoryPoint() {
        return storyPoint;
    }

    public void setStoryPoint(Double storyPoint) {
        this.storyPoint = storyPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public int getJiraIssueId() {
        return jiraIssueId;
    }

    public void setJiraIssueId(int jiraIssueId) {
        this.jiraIssueId = jiraIssueId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Jira getJira() {
        return jira;
    }

    public void setJira(Jira jira) {
        this.jira = jira;
    }
}
