package com.cockpit.api.controller;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.MvpDTO;
import com.cockpit.api.service.AuthService;
import com.cockpit.api.service.MvpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
public class MvpController {

    Logger log = LoggerFactory.getLogger(MvpController.class);

    private final MvpService mvpService;
    private final AuthService authService;

    @Autowired
    public MvpController(MvpService mvpService, AuthService authService) {
        this.mvpService = mvpService;
        this.authService = authService;
    }

    // CREATE new MVP
    @PostMapping(value = "/api/v1/mvp/create")
    public ResponseEntity createMvp(@RequestHeader("Authorization") String authHeader,
                                    @RequestBody MvpDTO mvpDTO) {
        if (authService.isUserAuthorized(authHeader)) {
            MvpDTO newMvp = mvpService.createNewMvp(mvpDTO);
            return ResponseEntity.ok().body(newMvp);
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // GET MVP BY ID
    @GetMapping(value = "/api/v1/mvp/{id}")
    public ResponseEntity getMvp(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                MvpDTO mvpDTO = mvpService.findMvpById(id);
                return ResponseEntity.ok().body(mvpDTO);
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // GET ALL MVP
    @GetMapping(value = "/api/v1/mvp/all")
    public ResponseEntity findAllMvps(@RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            List<MvpDTO> mvpList = mvpService.findAllMvp();
            return ResponseEntity.ok(mvpList);
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // UPDATE
    @PutMapping(value = "/api/v1/mvp/update")
    public ResponseEntity updateMvp(@RequestBody MvpDTO mvpDTO, @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                MvpDTO mvpUpdated = mvpService.updateMvp(mvpDTO);
                return ResponseEntity.ok().body(mvpUpdated);
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // DELETE
    @DeleteMapping(value = "/api/v1/mvp/delete/{id}")
    public ResponseEntity deleteMvp(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                mvpService.deleteMvp(id);
                return ResponseEntity.ok("One Mvp has been deleted");
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // ASSIGN TEAM
    @PutMapping(value = "/api/v1/mvp/{id}/assignTeam/{teamId}")
    public ResponseEntity assignTeamToMvp(@PathVariable("id") Long id, @PathVariable("teamId") Long teamId,
                                          @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                MvpDTO mvp = mvpService.assignTeamOfMvp(id, teamId);
                return ResponseEntity.ok().body(mvp);
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // UNASSIGN TEAM
    @PutMapping(value = "/api/v1/mvp/unassignTeam/{id}")
    public ResponseEntity unassignTeamToMvp(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                MvpDTO mvp = mvpService.unassignTeamOfMvp(id);
                return ResponseEntity.ok().body(mvp);
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }
}
