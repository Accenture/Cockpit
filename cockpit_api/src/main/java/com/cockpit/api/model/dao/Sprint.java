package com.cockpit.api.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "sprint")
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int jiraSprintId;

    private Date sprintStartDate;

    private Date sprintEndDate;

    private Date sprintCompleteDate;

    private Integer teamMotivation;

    private Integer teamMood;

    private Integer teamConfidence;

    private Integer totalNbUs;

    private int sprintNumber;

    private String state;

    private Integer notCompletedUsNumber;

    private Integer completedUsNumber;

    private Integer puntedUsNumber;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_jira", nullable = false)
    @JsonIgnore
    private Jira jira;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<UserStory> userStories;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL)
    private Set<Impediment> impediments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getJiraSprintId() {
        return jiraSprintId;
    }

    public void setJiraSprintId(int jiraSprintId) {
        this.jiraSprintId = jiraSprintId;
    }

    public Date getSprintStartDate() {
        return sprintStartDate;
    }

    public void setSprintStartDate(Date sprintStartDate) {
        this.sprintStartDate = sprintStartDate;
    }

    public Date getSprintEndDate() {
        return sprintEndDate;
    }

    public void setSprintEndDate(Date sprintEndDate) {
        this.sprintEndDate = sprintEndDate;
    }

    public Date getSprintCompleteDate() {
        return sprintCompleteDate;
    }

    public void setSprintCompleteDate(Date sprintCompleteDate) {
        this.sprintCompleteDate = sprintCompleteDate;
    }

    public Integer getTeamMotivation() {
        return teamMotivation;
    }

    public void setTeamMotivation(Integer teamMotivation) {
        this.teamMotivation = teamMotivation;
    }

    public Integer getTeamMood() {
        return teamMood;
    }

    public void setTeamMood(Integer teamMood) {
        this.teamMood = teamMood;
    }

    public Integer getTeamConfidence() {
        return teamConfidence;
    }

    public void setTeamConfidence(Integer teamConfidence) {
        this.teamConfidence = teamConfidence;
    }


    public Integer getTotalNbUs() {
        return totalNbUs;
    }


    public void setTotalNbUs(Integer totalNbUs) {
        this.totalNbUs = totalNbUs;
    }

    public int getSprintNumber() {
        return sprintNumber;
    }

    public void setSprintNumber(int sprintNumber) {
        this.sprintNumber = sprintNumber;
    }

    public Jira getJira() {
        return jira;
    }

    public void setJira(Jira jira) {
        this.jira = jira;
    }

    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }

    public Set<Impediment> getImpediments() {
        return impediments;
    }

    public void setImpediments(Set<Impediment> impediments) {
        this.impediments = impediments;
    }

    public Integer getNotCompletedUsNumber() {
        return notCompletedUsNumber;
    }

    public void setNotCompletedUsNumber(Integer notCompletedUsNumber) {
        this.notCompletedUsNumber = notCompletedUsNumber;
    }

    public Integer getPuntedUsNumber() {
        return puntedUsNumber;
    }

    public void setPuntedUsNumber(Integer puntedUsNumber) {
        this.puntedUsNumber = puntedUsNumber;
    }

    public Integer getCompletedUsNumber() {
        return completedUsNumber;
    }

    public void setCompletedUsNumber(Integer completedUsNumber) {
        this.completedUsNumber = completedUsNumber;
    }
    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", sprintNumber=" + sprintNumber +
                '}';
    }

}
