package com.cockpit.api.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade=CascadeType.ALL)
    private Set<TeamMember> teamMembers;

    @OneToMany
    @JsonIgnore
    private Set<Mvp> mvps;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeamMembers(Set<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Long getId() {
        return id;
    }

    public void setMvps(Set<Mvp> mvps) { this.mvps = mvps; }

    public String getName() {
        return name;
    }

    public Set<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public Set<Mvp> getMvps() { return mvps; }
}
