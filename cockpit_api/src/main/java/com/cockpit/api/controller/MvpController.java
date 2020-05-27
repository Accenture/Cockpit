package com.cockpit.api.controller;

import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dto.MvpDTO;
import com.cockpit.api.repository.MvpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MvpController {

    private final MvpRepository mvpRepository;

    @Autowired
    public MvpController(MvpRepository mvpRepository) {
        this.mvpRepository = mvpRepository;
    }

    // CREATE new MVP
    @PostMapping(
            value = "/api/v1/mvp/create"
    )
    public ResponseEntity<Mvp> createMvp(@RequestBody MvpDTO mvpDTO) {
        Mvp newMvp = new Mvp();
        newMvp.setName(mvpDTO.getName());
        newMvp.setCycle(mvpDTO.getCycle());
        newMvp.setEntity(mvpDTO.getEntity());
        newMvp.setMvpDescription(mvpDTO.getMvpDescription());
        newMvp.setStatus(mvpDTO.getStatus());
        newMvp.setUrlMvpAvatar(mvpDTO.getUrlMvpAvatar());
        newMvp.setJira(mvpDTO.getJira());
        newMvp.setTeam(mvpDTO.getTeam());
        newMvp.setTechnologies(mvpDTO.getTechnologies());
        mvpRepository.save(newMvp);
        return ResponseEntity.ok(newMvp);
    }

    // GET MVP BY ID
    @GetMapping(
            value = "/api/v1/mvp/{id}"
    )
    public ResponseEntity<Optional<Mvp>> getMvp(@PathVariable Long id) {
        Optional<Mvp> mvpRes = mvpRepository.findById(id);
        return ResponseEntity.ok(mvpRes);
    }

    // GET ALL MVP
    @GetMapping(
            value = "/api/v1/mvp/all"
    )
    public ResponseEntity<List<Mvp>> findAllMvps() {
        List<Mvp> mvpList = mvpRepository.findAllByOrderByName();
        return ResponseEntity.ok(mvpList);
    }

    // UPDATE
    @PutMapping(
            value = "/api/v1/mvp/update/{id}"
    )
    public ResponseEntity<Mvp> updateMvp(@RequestBody MvpDTO mvpDTO, @PathVariable Long id) {
        Optional<Mvp> mvpRes = mvpRepository.findById(id);
        return mvpRes.map(mvpUpdate->{
            mvpUpdate.setName(mvpDTO.getName());
            mvpUpdate.setCycle(mvpDTO.getCycle());
            mvpUpdate.setEntity(mvpDTO.getEntity());
            mvpUpdate.setMvpDescription(mvpDTO.getMvpDescription());
            mvpUpdate.setStatus(mvpDTO.getStatus());
            mvpUpdate.setUrlMvpAvatar(mvpDTO.getUrlMvpAvatar());
            mvpUpdate.setJira(mvpDTO.getJira());
            mvpUpdate.setTeam(mvpDTO.getTeam());
            mvpUpdate.setTechnologies(mvpDTO.getTechnologies());
            mvpRepository.save(mvpUpdate);
                return ResponseEntity.ok(mvpUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("Mvp Not found"));
    }

    // DELETE
    @DeleteMapping(
            value = "/api/v1/mvp/delete/{id}"
    )
    public ResponseEntity<String> deleteMvp(@PathVariable Long id) {
        return mvpRepository.findById(id)
                .map(mvpDelete ->{
                    mvpRepository.delete(mvpDelete);
                    return ResponseEntity.ok("One Mvp has been deleted");
                }).orElseThrow(()-> new ResourceNotFoundException("Mvp not found"));
    }
}
