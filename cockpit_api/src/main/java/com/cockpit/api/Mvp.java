package com.cockpit.api;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Mvp extends EntityWithUUID{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    private String entity;
    private String urlMvpAvatar;
    private int cycle;
    private String mvpDescription;
    private String status;
    @ManyToMany
    Set<Technology> technologies;
    @OneToOne
    @JoinColumn(name="id_jira")
    private Jira jira;

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

    public Set<Technology> getTechnologies() {
        return technologies;
    }

    public Jira getJira() {
        return jira;
    }
}
