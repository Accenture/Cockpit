package com.cockpit.api.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team{
    @Id
    @GeneratedValue(generator = "question_generator")
    @SequenceGenerator(
            name = "question_generator",
            sequenceName = "question_sequence",
            initialValue = 1000
    )
    private Long id;
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
