package com.cockpit.api.controller;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dto.MvpDTO;
import com.cockpit.api.service.MvpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
public class MvpController {

    Logger log = LoggerFactory.getLogger(MvpController.class);

    private final MvpService mvpService;

    @Autowired
    public MvpController(MvpService mvpService) {
        this.mvpService = mvpService;
    }

    // CREATE new MVP
    @PostMapping(
            value = "/api/v1/mvp/create"
    )
    public ResponseEntity<MvpDTO> createMvp(@RequestBody MvpDTO mvpDTO) {
        MvpDTO newMvp = mvpService.createNewMvp(mvpDTO);
        return ResponseEntity.ok().body(newMvp);
    }

    // GET MVP BY ID
    @GetMapping(
            value = "/api/v1/mvp/{id}"
    )
    public ResponseEntity getMvp(@PathVariable Long id) {
        try {
            MvpDTO mvpDTO = mvpService.findMvpById(id);
            return ResponseEntity.ok().body(mvpDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET ALL MVP
    @GetMapping(
            value = "/api/v1/mvp/all"
    )
    public ResponseEntity<List<MvpDTO>> findAllMvps() {
        List<MvpDTO> mvpList = mvpService.findAllMvp();
        return ResponseEntity.ok(mvpList);
    }

    // UPDATE
    @PutMapping(
            value = "/api/v1/mvp/update"
    )
    public ResponseEntity updateMvp(@RequestBody MvpDTO mvpDTO) {
        try {
            MvpDTO mvpUpdated = mvpService.updateMvp(mvpDTO);
            return  ResponseEntity.ok().body(mvpUpdated);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping(
            value = "/api/v1/mvp/delete/{id}"
    )
    public ResponseEntity<String> deleteMvp(@PathVariable Long id) {
        try {
            mvpService.deleteMvp(id);
            return ResponseEntity.ok("One Mvp has been deleted");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
