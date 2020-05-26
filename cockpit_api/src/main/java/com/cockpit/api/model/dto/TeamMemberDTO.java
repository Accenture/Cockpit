package com.cockpit.api.model.dto;

import com.cockpit.api.model.dao.Team;
import java.util.Set;

public class TeamMemberDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Set<Team> teams;

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
