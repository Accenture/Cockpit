package com.cockpit.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	private ModelMapper modelMapper = new ModelMapper();
	
    @Autowired
    public SprintController(
            SprintService sprintService,  JiraService jiraService
    ) {
        this.sprintService = sprintService;
        this.jiraService=jiraService;
    }

    // CREATE a new Sprint
    @PostMapping(
            value = "/api/v1/sprint/create"
    )
    public ResponseEntity<SprintDTO> createSprint(@RequestBody SprintDTO sprintDTO) {
        SprintDTO newSprint = sprintService.createNewSprint(sprintDTO);
        return ResponseEntity.ok(newSprint);
    }

    // GET Sprint BY ID
    @GetMapping(
            value = "/api/v1/sprint/{id}"
    )
    public ResponseEntity getSprintById(@PathVariable Long id) {
        try {
            SprintDTO sprintFound = sprintService.findSprintById(id);
            return ResponseEntity.ok().body(sprintFound);
        } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // UPDATE a Sprint
    @PutMapping(
            value = "/api/v1/sprint/update/{id}"
    )
    public ResponseEntity updateSprint(@RequestBody SprintDTO sprintDTO, @RequestParam Long id) {
        try {
            SprintDTO sprintUpdated = sprintService.updateSprint(sprintDTO, id);
            return ResponseEntity.ok().body(sprintUpdated);
        } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE a Sprint
    @DeleteMapping(
            value = "/api/v1/sprint/delete/{id}"
    )
    public ResponseEntity<String> deleteSprint(@PathVariable Long id) {
        try {
            sprintService.deleteSprint(id);
            return ResponseEntity.ok("One Sprint has been deleted");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // GET Sprint BY Sprint Number And Jira
    @GetMapping(
            value = "/api/v1/sprint/{jiraId}/{sprintNumber}"
    )
    public ResponseEntity getSprintBySprintNumberAndJira(@PathVariable Long jiraId, @PathVariable int sprintNumber) {
        try {
        	JiraDTO jira = jiraService.findJiraById(jiraId);
            Sprint sprintFound = sprintService.findByMvpAndSprintNumber(modelMapper.map(jira, Jira.class), sprintNumber);
            return ResponseEntity.ok().body(sprintFound);
        } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
