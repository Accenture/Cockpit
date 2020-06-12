package com.cockpit.api.controller;
import com.cockpit.api.service.jiraGatewayService.JiraGatewayService;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dto.JiraDTO;
import com.cockpit.api.service.JiraService;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.jayway.jsonpath.JsonPath.parse;

@RestController
public class JiraController {

    Logger log = LoggerFactory.getLogger(JiraController.class);

    private final JiraService jiraService;
    private final JiraGatewayService jiraGatewayService;

    @Autowired
    public JiraController(JiraService jiraService, JiraGatewayService jiraGatewayService) {
        this.jiraService = jiraService;
        this.jiraGatewayService = jiraGatewayService;
    }

    // CREATE new JIRA
    @PostMapping(
            value = "/api/v1/jira/create"
    )
    public ResponseEntity<JiraDTO> createJira(@RequestBody JiraDTO jiraDTO) {
        JiraDTO newJira = jiraService.createNewJiraProject(jiraDTO);
        return ResponseEntity.ok().body(newJira);
    }

    // GET JIRA BY ID
    @GetMapping(
            value = "/api/v1/jira/{id}"
    )
    public ResponseEntity getJira(@PathVariable Long id) {
        try {
            JiraDTO jiraDTO = jiraService.findJiraById(id);
            return ResponseEntity.ok().body(jiraDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // UPDATE
    @PutMapping(
            value = "/api/v1/jira/update"
    )
    public ResponseEntity updateJira(@RequestBody JiraDTO jiraDTO) {
        try {
            JiraDTO jiraUpdated = jiraService.updateJira(jiraDTO);
            return  ResponseEntity.ok().body(jiraUpdated);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping(
            value = "/api/v1/jira/delete/{id}"
    )
    public ResponseEntity<String> deleteJira(@PathVariable Long id) {
        try {
            jiraService.deleteJira(id);
            return ResponseEntity.ok("One Jira Project has been deleted");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
