package com.cockpit.api;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Team extends EntityWithUUID{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    @ManyToMany
    Set<TeamMember> teamMembers;

    public void setName(String name) {
        this.name = name;
    }

    public void setTeamMembers(Set<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getName() {
        return name;
    }

    public Set<TeamMember> getTeamMembers() {
        return teamMembers;
    }
}
