package com.cockpit.api.model.dao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Mvp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message="Name is mandatory")
    private String name;

    @NotNull(message="Entity is mandatory")
    private String entity;

    @NotNull(message="Avatar url is mandatory")
    private String urlMvpAvatar;

    @NotNull(message="Cycle is mandatory")
    private int cycle;

    @NotNull(message="MVP Description is mandatory")
    private String mvpDescription;

    private String status;

    @OneToMany(cascade=CascadeType.ALL)
    private Set<Sprint> sprints;

    @OneToMany(cascade=CascadeType.ALL)
    private Set<UserStory> userStories;

    @ManyToOne(cascade=CascadeType.ALL)
    private Team team;

    @ManyToMany(cascade=CascadeType.ALL)
    private Set<Technology> technologies;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "jira_id" )
    private Jira jira;

    public Mvp() {
        // Empty constructor
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

    public void setTeam(Team team) { this.team = team; }

    public void setTechnologies(Set<Technology> technologies) {
        this.technologies = technologies;
    }

    public void setJira(Jira jira) {
        this.jira = jira;
    }

    public void setSprints(Set<Sprint> sprints) { this.sprints = sprints; }

    public void setUserStories(Set<UserStory> userStories) { this.userStories = userStories; }

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

    public Set<Sprint> getSprints() { return sprints; }

    public Set<UserStory> getUserStories() { return userStories; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

}
