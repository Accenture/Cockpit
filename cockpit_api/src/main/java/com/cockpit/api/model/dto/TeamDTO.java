package com.cockpit.api.model.dto;

import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dao.TeamMember;
import java.util.Set;

public class TeamDTO{

    private Long id;

    private String name;

    private Set<TeamMember> teamMembers;

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

    public void setMvps(Set<Mvp> mvps) { this.mvps = mvps; }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public Set<Mvp> getMvps() { return mvps; }
}
