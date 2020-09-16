package com.cockpit.api.controller;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dao.TeamMember;
import com.cockpit.api.model.dao.Technology;
import com.cockpit.api.model.dto.TechnologyDTO;
import com.cockpit.api.service.AuthService;
import com.cockpit.api.service.MvpService;
import com.cockpit.api.service.TechnologyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TechnologyController {
    private final TechnologyService technologyService;
    private final AuthService authService;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public TechnologyController(TechnologyService technologyService, AuthService authService) {
        this.technologyService = technologyService;
        this.authService = authService;
    }

    // CREATE a new Technology
    @PostMapping(
            value = "/api/v1/technology/{id}/create"
    )
    public ResponseEntity createTechnology(@RequestBody TechnologyDTO technologyDTO, @PathVariable Long id,
                                           @RequestHeader("Authorization") String authHeader) throws ResourceNotFoundException {
        if (authService.isScrumMaster(authHeader)) {
            TechnologyDTO newTechnology = technologyService.createNewTechnology(technologyDTO, id);
            return ResponseEntity.ok(newTechnology);
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // GET Technology BY ID
    @GetMapping(
            value = "/api/v1/technology/{id}"
    )
    public ResponseEntity<Object> getTechnology(@PathVariable Long id,
                                        @RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            try {
                TechnologyDTO technologyFound = technologyService.findTechnologyById(id);
                return ResponseEntity.ok().body(technologyFound);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // GET ALL Technologies
    @GetMapping(
            value = "/api/v1/technology/all"
    )
    public ResponseEntity<Object> findAllTechnologies(@RequestHeader("Authorization") String authHeader) {
        if (authService.isUserAuthorized(authHeader)) {
            List<TechnologyDTO> technologyList = technologyService.findAllTechnology();
            return ResponseEntity.ok(technologyList);
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // UPDATE
    @PutMapping(
            value = "/api/v1/technology/update/{id}"
    )
    public ResponseEntity<Object> updateTechnology(@RequestBody TechnologyDTO technologyDTO,
                                           @PathVariable Long id,
                                           @RequestHeader("Authorization") String authHeader) {
        if (authService.isScrumMaster(authHeader)) {
            try {
                TechnologyDTO technologyUpdated = technologyService.updateTechnology(technologyDTO, id);
                return ResponseEntity.ok().body(technologyUpdated);
            } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }

    // DELETE
    @DeleteMapping(
            value = "/api/v1/technology/delete/{id}"
    )
    public ResponseEntity<Object> deleteTechnology(@PathVariable Long id,
                                           @RequestHeader("Authorization") String authHeader) {
        if (authService.isScrumMaster(authHeader)) {
            try {
                technologyService.deleteTechnology(id);
                return ResponseEntity.ok("One Technology has been deleted");
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }
}
