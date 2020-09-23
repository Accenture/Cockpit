package com.cockpit.api.model.dto;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Team;
import com.cockpit.api.model.dao.Technology;
import java.util.Set;

public class MvpDTO {

    private Long id;

    private String name;

    private String entity;

    private String urlMvpAvatar;

    private int cycle;

    private int scopeCommitment;
    
    private Integer sprintNumber;

    private String mvpDescription;

    private String status;

    private String technicalDebt;

    private Team team;

    private Set<Technology> technologies;

    private Jira jira;

    public int getScopeCommitment() {
        return scopeCommitment;
    }

    public void setScopeCommitment(int scopeCommitment) {
        this.scopeCommitment = scopeCommitment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public void setUrlMvpAvatar(String urlMvpAvatar) {
        this.urlMvpAvatar = urlMvpAvatar;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public void setMvpDescription(String mvpDescription) {
        this.mvpDescription = mvpDescription;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTechnicalDebt(String technicalDebt) {
        this.technicalDebt = technicalDebt;
    }

    public String getTechnicalDebt() {
        return technicalDebt;
    }

    public void setTeam(Team team) { this.team = team; }

    public void setTechnologies(Set<Technology> technologies) {
        this.technologies = technologies;
    }

    public void setJira(Jira jira) {
        this.jira = jira;
    }

    public String getName() {
        return name;
    }

    public String getEntity() {
        return entity;
    }

    public String getUrlMvpAvatar() {
        return urlMvpAvatar;
    }

    public int getCycle() {
        return cycle;
    }

    public String getMvpDescription() {
        return mvpDescription;
    }

    public String getStatus() {
        return status;
    }

    public Team getTeam() {
        return team;
    }

    public Set<Technology> getTechnologies() {
        return technologies;
    }

    public Jira getJira() {
        return jira;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

	public Integer getSprintNumber() {
		return sprintNumber;
	}

	public void setSprintNumber(Integer sprintNumber) {
		this.sprintNumber = sprintNumber;
	}
    
    
}
