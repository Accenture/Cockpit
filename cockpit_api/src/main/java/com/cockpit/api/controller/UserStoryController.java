package com.cockpit.api.controller;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dto.UserStoryDTO;
import com.cockpit.api.service.AuthService;
import com.cockpit.api.service.UserStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserStoryController {
    private final UserStoryService userStoryService;
    private final AuthService authService;

    @Autowired
    public UserStoryController(
            UserStoryService userStoryService,
            AuthService authService
    ) {
        this.userStoryService = userStoryService;
        this.authService = authService;
    }

    // CREATE a new User Story
    @PostMapping(
            value = "/api/v1/user-story/create"
    )
    public ResponseEntity createUserStory(@RequestBody UserStoryDTO userStoryDTO,
                                          @RequestHeader("Authorization") String authHeader) {
        if (authService.isScrumMaster(authHeader)) {
            UserStoryDTO newUserStory = userStoryService.createNewUserStory(userStoryDTO);
            return ResponseEntity.ok(newUserStory);
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // GET User Story BY ID
    @GetMapping(
            value = "/api/v1/user-story/{id}"
    )
    public ResponseEntity getUserStoryById(@PathVariable Long id,
                                           @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                UserStoryDTO userStoryFound = userStoryService.findUserStoryById(id);
                return ResponseEntity.ok().body(userStoryFound);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // UPDATE a User Story
    @PutMapping(
            value = "/api/v1/user-story/update/{id}"
    )
    public ResponseEntity updateUserStory(@RequestBody UserStoryDTO userStoryDTO,
                                          @PathVariable Long id,
                                          @RequestHeader("Authorization") String authHeader) {
        if (authService.isScrumMaster(authHeader)) {
            try {
                UserStoryDTO userStoryUpdated = userStoryService.updateUserStory(userStoryDTO, id);
                return ResponseEntity.ok().body(userStoryUpdated);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // DELETE a User Story
    @DeleteMapping(
            value = "/api/v1/user-story/delete/{id}"
    )
    public ResponseEntity deleteUserStory(@PathVariable Long id,
                                          @RequestHeader("Authorization") String authHeader) {
        if (authService.isScrumMaster(authHeader)) {
            try {
                userStoryService.deleteUserStory(id);
                return ResponseEntity.ok("One User Story has been deleted");
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }
}
