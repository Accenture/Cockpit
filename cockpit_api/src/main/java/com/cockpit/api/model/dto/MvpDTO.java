package com.cockpit.api.model.dto;

import com.cockpit.api.model.dao.Jira;
<<<<<<< HEAD
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dao.Team;
import com.cockpit.api.model.dao.Technology;
import com.cockpit.api.model.dao.UserStory;

=======
import com.cockpit.api.model.dao.Team;
import com.cockpit.api.model.dao.Technology;
>>>>>>> CP-73-createNewBackend
import java.util.Set;

public class MvpDTO {

    private Long id;

    private String name;

    private String entity;

    private String urlMvpAvatar;

    private int cycle;

    private String mvpDescription;

    private String status;

    private Team team;

    private Set<Technology> technologies;

    private Jira jira;
<<<<<<< HEAD
    
    private Set<Sprint> sprints;
    
    private Set<UserStory> userStories;
=======
>>>>>>> CP-73-createNewBackend

    public MvpDTO() {
        // Empty constructor
    }


    public void setId(Long id) { this.id = id; }

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

    public void setTeam(Team team) { this.team = team; }


    public void setTechnologies(Set<Technology> technologies) {
        this.technologies = technologies;
    }

    public void setJira(Jira jira) {
        this.jira = jira;
    }

    public Long getId() { return id; }

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
<<<<<<< HEAD


	public Set<Sprint> getSprints() {
		return sprints;
	}


	public void setSprints(Set<Sprint> sprints) {
		this.sprints = sprints;
	}


	public Set<UserStory> getUserStories() {
		return userStories;
	}


	public void setUserStories(Set<UserStory> userStories) {
		this.userStories = userStories;
	}
    
=======
>>>>>>> CP-73-createNewBackend
}
