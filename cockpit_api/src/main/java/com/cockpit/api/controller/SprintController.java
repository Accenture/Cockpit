package com.cockpit.api.controller;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dto.SprintDTO;
import com.cockpit.api.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SprintController {
    private final SprintService sprintService;

    @Autowired
    public SprintController(
            SprintService sprintService
    ) {
        this.sprintService = sprintService;
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
}
