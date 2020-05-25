package com.cockpit.api.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Team{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(
            name = "team",
            sequenceName = "team_sequence",
            initialValue = 1000
    )
    private Long id;
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
