package com.cockpit.api.controller;


import com.cockpit.api.model.Mvp;
import com.cockpit.api.repository.MvpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@RestController
public class MvpController {
    @Autowired
    MvpRepository mvpRepository;

    // CREATE
    @PostMapping(
            value = "/api/v1/mvp/create"
    )
    public @ResponseBody ResponseEntity<Mvp> createMvp(@RequestBody Mvp mvp) {
        Mvp newMvp = new Mvp();
        newMvp.setName(mvp.getName());
        newMvp.setCycle(mvp.getCycle());
        newMvp.setEntity(mvp.getEntity());
        newMvp.setMvpDescription(mvp.getMvpDescription());
        newMvp.setStatus(mvp.getStatus());
        newMvp.setUrlMvpAvatar(mvp.getUrlMvpAvatar());
        mvpRepository.save(newMvp);
        return ResponseEntity.ok(newMvp);
    }

    // READ
    @GetMapping(
            value = "/api/v1/mvp/{id}"
    )
    public @ResponseBody ResponseEntity<Optional<Mvp>> getMvp(@PathVariable Long id) {
        Optional<Mvp> mvpRes = mvpRepository.findById(id);
        return ResponseEntity.ok(mvpRes);
    }

    // UPDATE
    @PutMapping(
            value = "/api/v1/mvp/update/{id}"
    )
    public @ResponseBody ResponseEntity<Mvp> updateMvp(@RequestBody Mvp mvp, @PathVariable Long id) {
        Optional<Mvp> mvpRes = mvpRepository.findById(id);
        return mvpRes.map(mvpUpdate->{
                mvpUpdate.setName(mvp.getName());
                mvpUpdate.setCycle(mvp.getCycle());
                mvpUpdate.setEntity(mvp.getEntity());
                mvpUpdate.setMvpDescription(mvp.getMvpDescription());
                mvpUpdate.setStatus(mvp.getStatus());
                mvpUpdate.setUrlMvpAvatar(mvp.getUrlMvpAvatar());
                mvpRepository.save(mvpUpdate);
                return ResponseEntity.ok(mvpUpdate);
            }).orElseThrow(() -> new ResourceNotFoundException("Mvp Not found"));
    }

    // DELETE
    @DeleteMapping(
            value = "/api/v1/mvp/delete/{id}"
    )
    public @ResponseBody ResponseEntity<?> deleteMvp(@PathVariable Long id) {
        return mvpRepository.findById(id)
                .map(mvpDelete ->{
                    mvpRepository.delete(mvpDelete);
                    return ResponseEntity.ok().build();
                }).orElseThrow(()-> new ResourceNotFoundException("Mvp not found"));
    }
}
