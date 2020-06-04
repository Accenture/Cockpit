package com.cockpit.api.model.dto;

import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dao.UserStory;

import java.util.Date;
import java.util.Set;

public class SprintDTO {

    private Long id;

    private int jiraSprintId;

    private Date sprintStartDate;

    private Date sprintEndDate;

    private Integer teamMotivation;

    private Integer teamMood;

    private Integer teamConfidence;

    private Integer totalNbUs;

    private int sprintNumber;

    private Mvp mvp;

    private Set<UserStory> userStories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Mvp getMvp() {
        return mvp;
    }

    public void setMvp(Mvp mvp) {
        this.mvp = mvp;
    }

    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }
}
