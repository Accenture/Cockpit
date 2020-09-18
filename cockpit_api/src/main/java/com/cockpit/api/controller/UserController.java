package com.cockpit.api.controller;
import com.cockpit.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final AuthService authService;

    @Autowired
    public UserController(AuthService authService) {
        this.authService = authService;
    }

    // Verify if user is Scrum Master
    @GetMapping(value = "/api/v1/user/isScrumMaster")
    public ResponseEntity<Boolean> isScrumMaster(@RequestHeader("Authorization") String authHeader) {

        try {
            boolean isScrumMaster= authService.isScrumMaster(authHeader);
            return ResponseEntity.ok().body(isScrumMaster);
        } catch (Exception e) {
            return ResponseEntity.ok().body(false);
        }
    }
}
