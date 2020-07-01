package com.cockpit.api.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "team_member")
public class TeamMember{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;
    
    private String role;
    
    private String urlTeamMemberAvatar;

    @ManyToMany(mappedBy = "teamMembers")
    @JsonIgnore
    private Set<Team> teams;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUrlTeamMemberAvatar() {
		return urlTeamMemberAvatar;
	}

	public void setUrlTeamMemberAvatar(String urlTeamMemberAvatar) {
		this.urlTeamMemberAvatar = urlTeamMemberAvatar;
	}
    
}
