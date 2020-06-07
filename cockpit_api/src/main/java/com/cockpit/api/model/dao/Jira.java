package com.cockpit.api.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "jira")
public class Jira{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jiraProjectKey;

    private int currentSprint;

    private int jiraProjectId;

    private Date mvpStartDate;

    private Date mvpEndDate;

    @OneToMany(cascade=CascadeType.ALL)
    private Set<Sprint> sprint;

    @OneToMany(cascade=CascadeType.ALL)
    private Set<UserStory> userStories;

    @OneToOne
    @JsonIgnore
    private Mvp mvp;

    public String getJiraProjectKey() {
        return jiraProjectKey;
    }

    public int getCurrentSprint() {
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

    public void setCurrentSprint(int currentSprint) {
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

    public Set<Sprint> getSprint() {
        return sprint;
    }

    public void setSprint(Set<Sprint> sprint) { this.sprint = sprint; }

    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }
}
