package com.cockpit.api.model;

import javax.persistence.*;

@Entity
@Table(name = "userstory")
public class UserStory {
    @Id
    @GeneratedValue(generator = "question_generator")
    @SequenceGenerator(
            name = "question_generator",
            sequenceName = "question_sequence",
            initialValue = 1000
    )
    private Long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String creationDate;
    private String endDate;
    private int storyPoint;
    private String description;
    private String issueKey;
    private int jiraIssueId;
    private String priority;
    private String startDate;
    private String status;
    private String summary;
    @OneToOne
    @JoinColumn(name="id_Jira")
    private Jira jira;
    @OneToOne
    @JoinColumn(name="id_Sprint")
    private Sprint sprint;

    public UserStory(){
    }

    public Long getId() {
        return id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getStoryPoint() {
        return storyPoint;
    }

    public String getDescription() {
        return description;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public int getJiraIssueId() {
        return jiraIssueId;
    }

    public String getPriority() {
        return priority;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStatus() {
        return status;
    }

    public String getSummary() {
        return summary;
    }

    public Jira getJira() {
        return jira;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStoryPoint(int storyPoint) {
        this.storyPoint = storyPoint;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public void setJiraIssueId(int jiraIssueId) {
        this.jiraIssueId = jiraIssueId;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setJira(Jira jira) {
        this.jira = jira;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }
}
