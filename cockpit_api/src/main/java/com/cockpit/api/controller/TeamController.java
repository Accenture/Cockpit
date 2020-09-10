package com.cockpit.api.controller;

import java.util.List;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.JiraDTO;
import com.cockpit.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dto.TeamDTO;
import com.cockpit.api.model.dto.TeamMemberDTO;
import com.cockpit.api.service.TeamService;

@RestController
@CrossOrigin
public class TeamController {
    private final TeamService teamService;
    private final AuthService authService;

    @Autowired
    public TeamController(TeamService teamService, AuthService authService) {
        this.teamService = teamService;
        this.authService = authService;
    }

    // CREATE a new Team and assign it to an MVP
    @PostMapping(value = "/api/v1/team/create/{mvpId}")
    public ResponseEntity createTeam(@PathVariable Long mvpId,
                                     @RequestBody TeamDTO teamDTO,
                                     @RequestHeader("Authorization") String authHeader)
            throws ResourceNotFoundException {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                TeamDTO newTeam = teamService.createNewTeam(teamDTO, mvpId);
                return ResponseEntity.ok(newTeam);

            } catch (Exception e) {
                return ResponseEntity.ok().body(null);
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // GET ALL
    @GetMapping(value = "/api/v1/team/all")
    public ResponseEntity getTeams(@RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            List<TeamDTO> teamList = teamService.findAll();
            return ResponseEntity.ok(teamList);
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // GET ALL MEMBERS
    @GetMapping(value = "/api/v1/teamMember/all")
    public ResponseEntity<Object> getAllMembers(@RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            List<TeamMemberDTO> memberList = teamService.findAllMembers();
            return ResponseEntity.ok(memberList);
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // GET Team BY ID
    @GetMapping(value = "/api/v1/team/{id}")
    public ResponseEntity getTeamById(@PathVariable Long id,
                                      @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                TeamDTO teamFound = teamService.findTeamById(id);
                return ResponseEntity.ok().body(teamFound);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // UPDATE a Team
    @PutMapping(value = "/api/v1/team/update/{id}")
    public ResponseEntity updateTeam(@RequestBody TeamDTO teamDTO,
                                     @PathVariable Long id,
                                     @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                TeamDTO teamUpdated = teamService.updateTeam(teamDTO, id);
                return ResponseEntity.ok().body(teamUpdated);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // DELETE a Team
    @DeleteMapping(value = "/api/v1/team/delete/{id}")
    public ResponseEntity deleteTeam(@PathVariable Long id,
                                     @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                teamService.deleteTeam(id);
                return ResponseEntity.ok("One Team has been deleted");
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // ADD a Team member
    @PutMapping(value = "/api/v1/team/addTeamMember/{id}")
    public ResponseEntity addTeamMember(@RequestBody TeamMemberDTO teamMemberDTO,
                                        @PathVariable Long id,
                                        @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                TeamDTO teamUpdated = teamService.createTeamMember(id, teamMemberDTO);
                return ResponseEntity.ok().body(teamUpdated);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // ASSIGN a Team member
    @PutMapping(value = "/api/v1/team/{id}/assignTeamMember/{teamMemberId}")
    public ResponseEntity<Object> assignTeamMember(@PathVariable("id") Long id,
                                                   @PathVariable("teamMemberId") Long teamMemberId,
                                                   @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                TeamDTO teamUpdated = teamService.assignTeamMember(id, teamMemberId);
                return ResponseEntity.ok().body(teamUpdated);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // DELETE a Team member
    @DeleteMapping(value = "/api/v1/team/{id}/deleteTeamMember/{teamMemberId}")
    public ResponseEntity deleteTeamMember(@PathVariable("id") Long id,
                                           @PathVariable("teamMemberId") Long teamMemberId,
                                           @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                TeamDTO teamUpdated = teamService.deleteTeamMember(id, teamMemberId);
                return ResponseEntity.ok().body(teamUpdated);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // UNASSIGN a Team member
    @PutMapping(value = "/api/v1/team/{id}/unassignTeamMember/{teamMemberId}")
    public ResponseEntity<Object> unassignTeamMember(@PathVariable("id") Long id,
                                                     @PathVariable("teamMemberId") Long teamMemberId,
                                                     @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                TeamDTO teamUpdated = teamService.unassignTeamMember(id, teamMemberId);
                return ResponseEntity.ok().body(teamUpdated);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }
}
