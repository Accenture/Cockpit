package com.cockpit.api.controller;

import com.cockpit.api.model.dao.Impediment;
import com.cockpit.api.model.dto.ImpedimentDTO;
import com.cockpit.api.model.dto.ObeyaDTO;
import com.cockpit.api.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.JiraDTO;
import com.cockpit.api.model.dto.SprintDTO;
import com.cockpit.api.service.JiraService;
import com.cockpit.api.service.SprintService;

@RestController
public class SprintController {
    private final SprintService sprintService;
    private final JiraService jiraService;
    private final AuthService authService;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public SprintController(
            SprintService sprintService, JiraService jiraService, AuthService authService
    ) {
        this.sprintService = sprintService;
        this.jiraService = jiraService;
        this.authService = authService;
    }

    // GET Sprint BY Sprint Number And Jira
    @GetMapping(
            value = "/api/v1/sprint/{jiraId}/{sprintNumber}"
    )
    public ResponseEntity<Object> getSprintBySprintNumberAndJira(@PathVariable Long jiraId,
                                                         @PathVariable int sprintNumber,
                                                         @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                JiraDTO jira = jiraService.findJiraById(jiraId);
                Sprint sprintFound = sprintService.findByJiraAndSprintNumber(modelMapper.map(jira, Jira.class), sprintNumber);
                return ResponseEntity.ok().body(sprintFound);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // UPDATE team health in sprint
    @PutMapping(
            value = "/api/v1/sprint/{jiraId}/updateTeamHealth/{sprintNumber}"
    )
    public ResponseEntity<Object> updateTeamHealth(@RequestBody ObeyaDTO obeya, @PathVariable Long jiraId, @PathVariable int sprintNumber, @RequestHeader("Authorization") String authHeader) {

        if (authService.isScrumMaster(authHeader)) {
            try {
                JiraDTO jira = jiraService.findJiraById(jiraId);
                Sprint sprintFound = sprintService.findByJiraAndSprintNumber(modelMapper.map(jira, Jira.class), sprintNumber);
                SprintDTO sprint = sprintService.setTeamHealth(obeya, sprintFound);
                return ResponseEntity.ok().body(sprint);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // ADD impediment to sprint
    @PutMapping(
            value = "/api/v1/sprint/{jiraId}/addImpediment/{sprintNumber}"
    )
    public ResponseEntity<Object> addImpediment(@RequestBody ImpedimentDTO impediment, @PathVariable Long jiraId, @PathVariable int sprintNumber, @RequestHeader("Authorization") String authHeader) {

        if (authService.isScrumMaster(authHeader)) {
            try {
                JiraDTO jira = jiraService.findJiraById(jiraId);
                Sprint sprintFound = sprintService.findByJiraAndSprintNumber(modelMapper.map(jira, Jira.class), sprintNumber);
                SprintDTO sprint = sprintService.addImpediment(modelMapper.map(impediment, Impediment.class), sprintFound);
                return ResponseEntity.ok().body(sprint);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

}
