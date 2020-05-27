package com.cockpit.api.controller;

import com.cockpit.api.model.dao.Technology;
import com.cockpit.api.model.dto.TechnologyDTO;
import com.cockpit.api.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TechnologyController {
    private final TechnologyRepository technologyRepository;

    @Autowired
    public TechnologyController(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    // CREATE a new Technology
    @PostMapping(
            value = "/api/v1/technology/create"
    )
    public ResponseEntity<Technology> createTechnology(@RequestBody TechnologyDTO technologyDTO) {
        Technology newTechnology = new Technology();
        newTechnology.setName(technologyDTO.getName());

        newTechnology.setUrl(technologyDTO.getUrl());
        technologyRepository.save(newTechnology);
        return ResponseEntity.ok(newTechnology);
    }

    // GET Technology BY ID
    @GetMapping(
            value = "/api/v1/technology/{id}"
    )
    public ResponseEntity<Optional<Technology>> getTechnology(@PathVariable Long id) {
        Optional<Technology> technologyRes = technologyRepository.findById(id);
        return ResponseEntity.ok(technologyRes);
    }

    // GET ALL Technologies
    @GetMapping(
            value = "/api/v1/technology/all"
    )
    public ResponseEntity<List<Technology>> findAllTechnologies() {
        List<Technology> technologyList = technologyRepository.findAllByOrderByName();
        return ResponseEntity.ok(technologyList);
    }

    // UPDATE
    @PutMapping(
            value = "/api/v1/technology/update/{id}"
    )
    public ResponseEntity<Technology> updateTechnology(@RequestBody TechnologyDTO technologyDTO, @PathVariable Long id) {
        Optional<Technology> technologyRes = technologyRepository.findById(id);
        return technologyRes.map(technologyToUpdate->{
            technologyToUpdate.setName(technologyDTO.getName());
            technologyToUpdate.setUrl(technologyDTO.getUrl());
            technologyRepository.save(technologyToUpdate);
            return ResponseEntity.ok(technologyToUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("Technology Not found"));
    }

    // DELETE
    @DeleteMapping(
            value = "/api/v1/technology/delete/{id}"
    )
    public ResponseEntity<String> deleteTechnology(@PathVariable Long id) {
        return technologyRepository.findById(id)
                .map(technologyToDelete ->{
                    technologyRepository.delete(technologyToDelete);
                    return ResponseEntity.ok("One technology has been deleted");
                }).orElseThrow(()-> new ResourceNotFoundException("Technology not found"));
    }
}
