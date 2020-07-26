package com.cockpit.api.controller;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dto.JiraDTO;
import com.cockpit.api.service.AuthService;
import com.cockpit.api.service.JiraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JiraController {

    Logger log = LoggerFactory.getLogger(JiraController.class);

    private final JiraService jiraService;
    private final AuthService authService;

    @Autowired
    public JiraController(JiraService jiraService, AuthService authService) {
        this.jiraService = jiraService;
        this.authService = authService;
    }

    // CREATE new JIRA
    @PostMapping(
            value = "/api/v1/jira/create"
    )
    public ResponseEntity createJira(@RequestBody JiraDTO jiraDTO,
                                     @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            JiraDTO newJira = jiraService.createNewJiraProject(jiraDTO);
            return ResponseEntity.ok().body(newJira);
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // GET JIRA BY ID
    @GetMapping(
            value = "/api/v1/jira/{id}"
    )
    public ResponseEntity getJira(@PathVariable Long id,
                                  @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                JiraDTO jiraDTO = jiraService.findJiraById(id);
                return ResponseEntity.ok().body(jiraDTO);
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // UPDATE
    @PutMapping(
            value = "/api/v1/jira/update"
    )
    public ResponseEntity updateJira(@RequestBody JiraDTO jiraDTO,
                                     @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                JiraDTO jiraUpdated = jiraService.updateJira(jiraDTO);
                return  ResponseEntity.ok().body(jiraUpdated);
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // DELETE
    @DeleteMapping(
            value = "/api/v1/jira/delete/{id}"
    )
    public ResponseEntity deleteJira(@PathVariable Long id,
                                             @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                jiraService.deleteJira(id);
                return ResponseEntity.ok("One Jira Project has been deleted");
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

}
