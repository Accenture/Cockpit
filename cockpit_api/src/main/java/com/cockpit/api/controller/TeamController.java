package com.cockpit.api.controller;

import com.cockpit.api.model.dao.Team;
import com.cockpit.api.model.dto.TeamDTO;
import com.cockpit.api.repository.TeamMemberRepository;
import com.cockpit.api.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class TeamController {
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    public TeamController(
            TeamRepository teamRepository,
            TeamMemberRepository teamMemberRepository
    ) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    // CREATE a new Team
    @PostMapping(
            value = "/api/v1/team/create"
    )
    public ResponseEntity<Team> createTeam(@RequestBody TeamDTO teamDTO) {
        Team newTeam = new Team();
        newTeam.setName(teamDTO.getName());

        newTeam.setTeamMembers(teamDTO.getTeamMembers());
        teamRepository.save(newTeam);
        return ResponseEntity.ok(newTeam);
    }

    // GET Team BY ID
    @GetMapping(
            value = "/api/v1/team/{id}"
    )
    public ResponseEntity<Optional<Team>> getTeamById(@PathVariable Long id) {
        Optional<Team> teamRes = teamRepository.findById(id);
        return ResponseEntity.ok(teamRes);
    }

    // UPDATE a Team
    @PutMapping(
            value = "/api/v1/team/update/{id}"
    )
    public ResponseEntity<Team> updateTeam(@RequestBody TeamDTO teamDTO, @PathVariable Long id) {
        Optional<Team> teamRes = teamRepository.findById(id);
        return teamRes.map(teamToUpdate -> {
            teamToUpdate.setName(teamDTO.getName());
            teamToUpdate.setTeamMembers(teamDTO.getTeamMembers());
            teamRepository.save(teamToUpdate);
            return ResponseEntity.ok(teamToUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("Team Not found"));
    }

    // DELETE a Team
    @DeleteMapping(
            value = "/api/v1/team/delete/{id}"
    )
    public ResponseEntity<String> deleteTeam(@PathVariable Long id) {
        return teamRepository.findById(id)
                .map(teamToDelete ->{
                    teamRepository.delete(teamToDelete);
                    return ResponseEntity.ok("One team has been deleted");
                }).orElseThrow(()-> new ResourceNotFoundException("Team not found"));
    }
}
