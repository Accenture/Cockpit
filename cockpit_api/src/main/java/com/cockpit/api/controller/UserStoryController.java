package com.cockpit.api.controller;

import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.model.dto.UserStoryDTO;
import com.cockpit.api.repository.UserStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserStoryController {
    private final UserStoryRepository userStoryRepository;

    @Autowired
    public UserStoryController(
            UserStoryRepository userStoryRepository
    ) {
        this.userStoryRepository = userStoryRepository;
    }

    // CREATE a new User Story
    @PostMapping(
            value = "/api/v1/userStory/create"
    )
    public ResponseEntity<UserStory> createUserStory(@RequestBody UserStoryDTO userStoryDTO) {
        UserStory newUserStory = new UserStory();
        newUserStory.setStatus(userStoryDTO.getStatus());
        newUserStory.setCreationDate(userStoryDTO.getCreationDate());
        newUserStory.setStartDate(userStoryDTO.getStartDate());
        newUserStory.setDoneDate(userStoryDTO.getDoneDate());
        newUserStory.setDescription(userStoryDTO.getDescription());
        newUserStory.setIssueKey(userStoryDTO.getIssueKey());
        newUserStory.setJiraIssueId(userStoryDTO.getJiraIssueId());
        newUserStory.setPriority(userStoryDTO.getPriority());
        newUserStory.setStoryPoint(userStoryDTO.getStoryPoint());
        newUserStory.setSummary(userStoryDTO.getSummary());
        userStoryRepository.save(newUserStory);
        return ResponseEntity.ok(newUserStory);
    }

    // GET User Story BY ID
    @GetMapping(
            value = "/api/v1/userStory/{id}"
    )
    public ResponseEntity<Optional<UserStory>> getUserStoryById(@PathVariable Long id) {
        Optional<UserStory> userStoryRes = userStoryRepository.findById(id);
        return ResponseEntity.ok(userStoryRes);
    }

    // UPDATE a User Story
    @PutMapping(
            value = "/api/v1/userStory/update/{id}"
    )
    public ResponseEntity<UserStory> updateUserStory(@RequestBody UserStoryDTO userStoryDTO, @PathVariable Long id) {
        Optional<UserStory> userStoryRes = userStoryRepository.findById(id);
        return userStoryRes.map(userStoryToUpdate -> {
            userStoryToUpdate.setStatus(userStoryDTO.getStatus());
            userStoryToUpdate.setCreationDate(userStoryDTO.getCreationDate());
            userStoryToUpdate.setStartDate(userStoryDTO.getStartDate());
            userStoryToUpdate.setDoneDate(userStoryDTO.getDoneDate());
            userStoryToUpdate.setDescription(userStoryDTO.getDescription());
            userStoryToUpdate.setIssueKey(userStoryDTO.getIssueKey());
            userStoryToUpdate.setJiraIssueId(userStoryDTO.getJiraIssueId());
            userStoryToUpdate.setPriority(userStoryDTO.getPriority());
            userStoryToUpdate.setStoryPoint(userStoryDTO.getStoryPoint());
            userStoryToUpdate.setSummary(userStoryDTO.getSummary());
            userStoryRepository.save(userStoryToUpdate);
            return ResponseEntity.ok(userStoryToUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("User Story Not found"));
    }

    // DELETE a User Story
    @DeleteMapping(
            value = "/api/v1/userStory/delete/{id}"
    )
    public ResponseEntity<String> deleteUserStory(@PathVariable Long id) {
        return userStoryRepository.findById(id)
                .map(userStoryToDelete ->{
                    userStoryRepository.delete(userStoryToDelete);
                    return ResponseEntity.ok("One User Story has been deleted");
                }).orElseThrow(()-> new ResourceNotFoundException("User Story not found"));
    }
}
