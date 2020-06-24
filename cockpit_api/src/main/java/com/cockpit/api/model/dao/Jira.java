package com.cockpit.api.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "jira")
public class Jira{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jiraProjectKey;
    
    private Integer currentSprint;

    private Integer jiraProjectId;

    private Date mvpStartDate;

    private Date mvpEndDate;

    @OneToMany(mappedBy = "jira", cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Sprint> sprints;

    @OneToMany(mappedBy = "jira", cascade=CascadeType.PERSIST)
    @JsonIgnore
    private Set<UserStory> userStories;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_mvp", nullable=false)
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

    public Set<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(Set<Sprint> sprint) { this.sprints = sprint; }

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
}
