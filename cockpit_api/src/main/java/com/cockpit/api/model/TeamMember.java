package com.cockpit.api.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class TeamMember{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(
            name = "team",
            sequenceName = "team_sequence",
            initialValue = 1000
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToMany
    Set<Team> teams;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<Team> getTeams() {
        return teams;
    }
}
