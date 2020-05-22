package com.cockpit.api.model;

import javax.persistence.*;

@Entity
public class Jira{
    @Id
    @GeneratedValue(generator = "question_generator")
    @SequenceGenerator(
            name = "question_generator",
            sequenceName = "question_sequence",
            initialValue = 1000
    )
    private Long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String jiraProjectKey;
    private int currentSprint;
    private int jiraProjectId;
    private String mvpStartDate;
    private String mvpEndDate;
    @OneToOne(mappedBy = "jira")
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

    public String getMvpStartDate() {
        return mvpStartDate;
    }

    public String getMvpEndDate() {
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

    public void setMvpStartDate(String mvpStartDate) {
        this.mvpStartDate = mvpStartDate;
    }

    public void setMvpEndDate(String mvpEndDate) {
        this.mvpEndDate = mvpEndDate;
    }

    public void setMvp(Mvp mvp) {
        this.mvp = mvp;
    }
}
