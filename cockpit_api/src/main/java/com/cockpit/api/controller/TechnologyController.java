package com.cockpit.api.controller;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dto.TechnologyDTO;
import com.cockpit.api.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
//@CrossOrigin
public class TechnologyController {
    private final TechnologyService technologyService;

    @Autowired
    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    // CREATE a new Technology
    @PostMapping(
            value = "/api/v1/technology/create"
    )
    public ResponseEntity<TechnologyDTO> createTechnology(@RequestBody TechnologyDTO technologyDTO) {
        TechnologyDTO newTechnology = technologyService.createNewTechnology(technologyDTO);
        return ResponseEntity.ok(newTechnology);
    }

    // GET Technology BY ID
    @GetMapping(
            value = "/api/v1/technology/{id}"
    )
    public ResponseEntity getTechnology(@PathVariable Long id) {
        try {
            TechnologyDTO technologyFound = technologyService.findTechnologyById(id);
            return ResponseEntity.ok().body(technologyFound);
        } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET ALL Technologies
    @GetMapping(
            value = "/api/v1/technology/all"
    )
    public ResponseEntity<List<TechnologyDTO>> findAllTechnologies() {
        List<TechnologyDTO> technologyList = technologyService.findAllTechnology();
        return ResponseEntity.ok(technologyList);
    }

    // UPDATE
    @PutMapping(
            value = "/api/v1/technology/update/{id}"
    )
    public ResponseEntity updateTechnology(@RequestBody TechnologyDTO technologyDTO, @PathVariable Long id) {
        try {
            TechnologyDTO technologyUpdated = technologyService.updateTechnology(technologyDTO, id);
            return ResponseEntity.ok().body(technologyUpdated);
        } catch (com.cockpit.api.exception.ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping(
            value = "/api/v1/technology/delete/{id}"
    )
    public ResponseEntity<String> deleteTechnology(@PathVariable Long id) {
        try {
            technologyService.deleteTechnology(id);
            return ResponseEntity.ok("One Technology has been deleted");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
