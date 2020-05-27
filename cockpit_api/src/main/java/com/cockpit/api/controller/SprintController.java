package com.cockpit.api.controller;

import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.SprintDTO;
import com.cockpit.api.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SprintController {
    private final SprintRepository sprintRepository;

    @Autowired
    public SprintController(
            SprintRepository sprintRepository
    ) {
        this.sprintRepository = sprintRepository;
    }

    // CREATE a new Sprint
    @PostMapping(
            value = "/api/v1/sprint/create"
    )
    public ResponseEntity<Sprint> createSprint(@RequestBody SprintDTO sprintDTO) {
        Sprint newSprint = new Sprint();
        newSprint.setJiraSprintId(sprintDTO.getJiraSprintId());
        newSprint.setSprintEndDate(sprintDTO.getSprintEndDate());
        newSprint.setSprintStartDate(sprintDTO.getSprintStartDate());
        newSprint.setSprintNumber(sprintDTO.getSprintNumber());
        newSprint.setTeamConfidence(sprintDTO.getTeamConfidence());
        newSprint.setTeamMood(sprintDTO.getTeamMood());
        newSprint.setTeamMotivation(sprintDTO.getTeamMotivation());
        newSprint.setTotalNbUs(sprintDTO.getTotalNbUs());
        newSprint.setUserStories(sprintDTO.getUserStories());
        sprintRepository.save(newSprint);
        return ResponseEntity.ok(newSprint);
    }

    // GET Sprint BY ID
    @GetMapping(
            value = "/api/v1/sprint/{id}"
    )
    public ResponseEntity<Optional<Sprint>> getSprintById(@PathVariable Long id) {
        Optional<Sprint> sprintRes = sprintRepository.findById(id);
        return ResponseEntity.ok(sprintRes);
    }

    // UPDATE a Sprint
    @PutMapping(
            value = "/api/v1/sprint/update/{id}"
    )
    public ResponseEntity<Sprint> updateSprint(@RequestBody SprintDTO sprintDTO, @PathVariable Long id) {
        Optional<Sprint> sprintRes = sprintRepository.findById(id);
        return sprintRes.map(sprintToUpdate -> {
            sprintToUpdate.setJiraSprintId(sprintDTO.getJiraSprintId());
            sprintToUpdate.setSprintEndDate(sprintDTO.getSprintEndDate());
            sprintToUpdate.setSprintStartDate(sprintDTO.getSprintStartDate());
            sprintToUpdate.setSprintNumber(sprintDTO.getSprintNumber());
            sprintToUpdate.setTeamConfidence(sprintDTO.getTeamConfidence());
            sprintToUpdate.setTeamMood(sprintDTO.getTeamMood());
            sprintToUpdate.setTeamMotivation(sprintDTO.getTeamMotivation());
            sprintToUpdate.setTotalNbUs(sprintDTO.getTotalNbUs());
            sprintToUpdate.setUserStories(sprintDTO.getUserStories());
            sprintRepository.save(sprintToUpdate);
            return ResponseEntity.ok(sprintToUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("Sprint Not found"));
    }

    // DELETE a Sprint
    @DeleteMapping(
            value = "/api/v1/sprint/delete/{id}"
    )
    public ResponseEntity<String> deleteSprint(@PathVariable Long id) {
        return sprintRepository.findById(id)
                .map(sprintToDelete ->{
                    sprintRepository.delete(sprintToDelete);
                    return ResponseEntity.ok("One Sprint has been deleted");
                }).orElseThrow(()-> new ResourceNotFoundException("Sprint not found"));
    }
}
