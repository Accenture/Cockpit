package com.cockpit.api.controller;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dto.JiraDTO;
import com.cockpit.api.model.dto.jira.Project;
import com.cockpit.api.service.AuthService;
import com.cockpit.api.service.JiraService;
import com.cockpit.api.service.jiragateway.JiraApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class JiraController {

    Logger log = LoggerFactory.getLogger(JiraController.class);

    private final JiraService jiraService;
    private final AuthService authService;
    private final JiraApiService jiraApiService;

    @Value("${spring.jira.urlVerifyValidProjectKey}")
    private String urlVerifyJiraKey;

    @Autowired
    public JiraController(JiraService jiraService, AuthService authService, JiraApiService jiraApiService) {
        this.jiraService = jiraService;
        this.authService = authService;
        this.jiraApiService = jiraApiService;
    }

    // CREATE new JIRA
    @PostMapping(
            value = "/api/v1/jira/create"
    )
    public ResponseEntity<Object> createJira(@RequestBody JiraDTO jiraDTO,
                                             @RequestHeader("Authorization") String authHeader) {
        if (authService.isScrumMaster(authHeader)) {
            String url = urlVerifyJiraKey + jiraDTO.getJiraProjectKey();
            try {
                jiraApiService.callJira(url, Project.class.getName());
                JiraDTO newJira = jiraService.createNewJiraProject(jiraDTO);
                return ResponseEntity.ok().body(newJira);
            } catch (Exception e) {
                return ResponseEntity.ok().body(null);
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }

    }

    // GET JIRA BY ID
    @GetMapping(
            value = "/api/v1/jira/{id}"
    )
    public ResponseEntity<Object> getJira(@PathVariable Long id,
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
    public ResponseEntity<Object> updateJira(@RequestBody JiraDTO jiraDTO,
                                             @RequestHeader("Authorization") String authHeader) {
        if (authService.isScrumMaster(authHeader)) {
            try {
                JiraDTO jiraUpdated = jiraService.updateJira(jiraDTO);
                return ResponseEntity.ok().body(jiraUpdated);
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
    public ResponseEntity<Object> deleteJira(@PathVariable Long id,
                                             @RequestHeader("Authorization") String authHeader) {
        if (authService.isScrumMaster(authHeader)) {
            try {
                jiraService.deleteJira(id);
                return ResponseEntity.ok().body(id);
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

}
