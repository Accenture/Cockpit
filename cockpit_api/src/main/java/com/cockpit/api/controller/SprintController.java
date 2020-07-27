package com.cockpit.api.controller;

import com.cockpit.api.model.dto.ObeyaDTO;
import com.cockpit.api.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.JiraDTO;
import com.cockpit.api.model.dto.SprintDTO;
import com.cockpit.api.service.JiraService;
import com.cockpit.api.service.SprintService;

@RestController
@CrossOrigin
public class SprintController {
    private final SprintService sprintService;
    private final JiraService jiraService;
    private final AuthService authService;
    private ModelMapper modelMapper = new ModelMapper();
	
    @Autowired
    public SprintController(
            SprintService sprintService,  JiraService jiraService, AuthService authService
    ) {
        this.sprintService = sprintService;
        this.jiraService = jiraService;
        this.authService = authService;
    }

    // CREATE a new Sprint
    @PostMapping(
            value = "/api/v1/sprint/create"
    )
    public ResponseEntity createSprint(@RequestBody SprintDTO sprintDTO,
                                                  @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            SprintDTO newSprint = sprintService.createNewSprint(sprintDTO);
            return ResponseEntity.ok(newSprint);
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // GET Sprint BY ID
    @GetMapping(
            value = "/api/v1/sprint/{id}"
    )
    public ResponseEntity getSprintById(@PathVariable Long id,
                                        @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                SprintDTO sprintFound = sprintService.findSprintById(id);
                return ResponseEntity.ok().body(sprintFound);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // UPDATE a Sprint
    @PutMapping(
            value = "/api/v1/sprint/update/{id}"
    )
    public ResponseEntity updateSprint(@RequestBody SprintDTO sprintDTO,
                                       @RequestParam Long id,
                                       @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                SprintDTO sprintUpdated = sprintService.updateSprint(sprintDTO, id);
                return ResponseEntity.ok().body(sprintUpdated);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // DELETE a Sprint
    @DeleteMapping(
            value = "/api/v1/sprint/delete/{id}"
    )
    public ResponseEntity deleteSprint(@PathVariable Long id,
                                               @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                sprintService.deleteSprint(id);
                return ResponseEntity.ok("One Sprint has been deleted");
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }
    // GET Sprint BY Sprint Number And Jira
    @GetMapping(
            value = "/api/v1/sprint/{jiraId}/{sprintNumber}"
    )
    public ResponseEntity getSprintBySprintNumberAndJira(@PathVariable Long jiraId,
                                                         @PathVariable int sprintNumber,
                                                         @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                JiraDTO jira = jiraService.findJiraById(jiraId);
                Sprint sprintFound = sprintService.findByMvpAndSprintNumber(modelMapper.map(jira, Jira.class), sprintNumber);
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
    public  ResponseEntity updateTeamHealth(@RequestBody ObeyaDTO obeya, @PathVariable Long jiraId, @PathVariable int sprintNumber)
    {
        try {
            JiraDTO jira = jiraService.findJiraById(jiraId);
            Sprint sprintFound = sprintService.findByMvpAndSprintNumber(modelMapper.map(jira, Jira.class), sprintNumber);
            sprintService.setTeamHealth(obeya, sprintFound);
            return ResponseEntity.ok().body(sprintFound);
        } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
