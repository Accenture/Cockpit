package com.cockpit.api;

import javax.persistence.*;
import java.util.Set;

@Entity
public class TeamMember extends EntityWithUUID{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
