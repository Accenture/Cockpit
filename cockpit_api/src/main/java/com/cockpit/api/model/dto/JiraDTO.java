package com.cockpit.api.model.dto;

import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dao.UserStory;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class JiraDTO {

    private Long id;

    private String jiraProjectKey;

    private Integer currentSprint;

    private int jiraProjectId;

    private Date mvpStartDate;

    private Date mvpEndDate;

    private List<Sprint> sprint;

    private Set<UserStory> userStories;

    private String state;

    private Mvp mvp;

    private Integer boardId;

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

    public String getJiraProjectKey() {
        return jiraProjectKey;
    }

    public Integer getCurrentSprint() {
        return currentSprint;
    }

    public int getJiraProjectId() {
        return jiraProjectId;
    }

    public Date getMvpStartDate() {
        return mvpStartDate;
    }

    public Date getMvpEndDate() {
        return mvpEndDate;
    }

    public Mvp getMvp() {
        return mvp;
    }

    public void setJiraProjectKey(String jiraProjectKey) {
        this.jiraProjectKey = jiraProjectKey;
    }

    public void setCurrentSprint(Integer currentSprint) {
        this.currentSprint = currentSprint;
    }

    public void setJiraProjectId(int jiraProjectId) {
        this.jiraProjectId = jiraProjectId;
    }

    public void setMvpStartDate(Date mvpStartDate) {
        this.mvpStartDate = mvpStartDate;
    }

    public void setMvpEndDate(Date mvpEndDate) {
        this.mvpEndDate = mvpEndDate;
    }

    public void setMvp(Mvp mvp) {
        this.mvp = mvp;
    }

    public List<Sprint> getSprint() {
        return sprint;
    }

    public void setSprint(List<Sprint> sprint) {
        this.sprint = sprint;
    }

    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

}
