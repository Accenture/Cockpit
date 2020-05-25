package com.cockpit.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Mvp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(
            name = "mvp",
            sequenceName = "mvp_sequence",
            initialValue = 1000
    )
    private Long id;
    @NotNull(message="Name is mandatory")
    private String name;
    @NotNull(message="Entity is mandatory")
    private String entity;
    private String urlMvpAvatar;
    @NotNull(message="Cycle is mandatory")
    private int cycle;
    @NotNull(message="MVP Description is mandatory")
    private String mvpDescription;
    private String status;
    @ManyToMany
    Set<Technology> technologies;
    @OneToOne
    @JoinColumn(name="jira_id")
    private Jira jira;


    public Mvp() {
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
