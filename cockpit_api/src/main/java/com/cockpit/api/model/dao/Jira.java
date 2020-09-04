package com.cockpit.api.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


import java.util.*;

@Entity
@Table(name = "jira")
public class Jira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String jiraProjectKey;

    private Integer currentSprint;

    private Integer jiraProjectId;

    private Date mvpStartDate;

    private Date mvpEndDate;

    private Integer boardId;

    @OneToMany(mappedBy = "jira", cascade = CascadeType.ALL)
    private List<Sprint> sprints;

    @OneToMany(mappedBy = "jira", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<UserStory> userStories;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_mvp", nullable = false)
    @JsonIgnore
    private Mvp mvp;

    public String getJiraProjectKey() {
        return jiraProjectKey;
    }

    public Integer getCurrentSprint() {
        return currentSprint;
    }

    public Integer getJiraProjectId() {
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

    public void setJiraProjectId(Integer jiraProjectId) {
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

    public List<Sprint> getSprints() {
        orderSprints();
        return sprints;
    }

    public void setSprints(List<Sprint> sprint) {
        this.sprints = sprint;
    }

    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public void orderSprints() {
        Collections.sort(sprints,new Comparator<Sprint>() {
            @Override
            public int compare(Sprint p1, Sprint p2) {
                return p1.getSprintNumber() - p2.getSprintNumber();
            }
        });
    }
}
