package cockpit.mvpinfos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.mvpinfos.exception.CockpitException;
import cockpit.mvpinfos.exception.ResourceNotFoundException;
import cockpit.mvpinfos.service.MvpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/1.0/mvp")
@Api(value = "${/api/1.0/mvp")
@CrossOrigin
@Slf4j
public class MvpController {

    private MvpService mvpService;
    @Autowired
    public MvpController(MvpService mvpService) {
        this.mvpService = mvpService;
    }

    @ApiOperation(value = "Get all MVPs")
    @RequestMapping(value = "/all", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Mvp> getAllMVPs() {
        List<Mvp> mvpList;
        try {
            mvpList = mvpService.getAllMvpByOrderByMvpStartDateDesc();
        } catch (ResourceNotFoundException e) {
            throw new CockpitException("1055", "Unable to load MVP List", e);
        }
        return mvpList;
    }

    @ApiOperation(value = "Get all MVPs")
    @RequestMapping(value = "/light/all", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Object[]> getAllLightMVPs() {
        List<Object[]> mvpList;
        try {
            mvpList = mvpService.getAllLightMVPs();
        } catch (ResourceNotFoundException e) {
            throw new CockpitException("1055", "Unable to load MVP List", e);

        }
        return mvpList;
    }

    @ApiOperation(value = "Get one MVP by id")
    @RequestMapping(value = "/{id}", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Mvp> getById(@PathVariable("id") String id) {

        Mvp mvp;
        try {
            mvp = mvpService.getById(id);
            return ResponseEntity.ok().body(mvp);
        } catch (ResourceNotFoundException e) {
            log.error("Not found mvp with the id: " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Update Mvp fields or create new mvp if not existing")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateMvpDetails(@PathVariable("id") String id, @RequestBody Mvp newMvp) {
        log.info("updating mvp[id: " + id + "]");
        Mvp mvpFound = null;
        try {
            mvpFound = mvpService.getById(id);
        } catch (ResourceNotFoundException e) {
            log.debug("mvp not found");
        }
        Mvp modifiedMvp;
        if (mvpFound == null) {
            Mvp nonExistingMvp = new Mvp();
            nonExistingMvp.setId(newMvp.getId());
            mvpService.initializeANewMvp(nonExistingMvp);
            modifiedMvp = mvpService.updateMvpInfo(nonExistingMvp, newMvp);
        } else {
            modifiedMvp = mvpService.updateMvpInfo(mvpFound, newMvp);
        }

        mvpService.updateMvp(modifiedMvp);
        log.info("mvp updated");
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Delete Mvp")
    @PutMapping(value = "/removeMvp/{id}")
    public ResponseEntity<?> deleteMvp(@PathVariable("id") String id) {
        log.info("Trying to remove mvp[id: " + id + "]");
        try {
            Mvp foundMvp = mvpService.getById(id);
            mvpService.deleteMvp(foundMvp);
            log.info("mvp[id: " + id + "] removed");
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            log.error("Cannot delete mvp[id: " + id + "]");
            log.error("deleteMvp : Resource not found", e);
            return ResponseEntity.status(500).build();
        }
    }

    @ApiOperation(value = "Assign team")
    @PutMapping(value = "/{id}/assignTeam/{teamId}")
    public ResponseEntity<?> assignTeamToMvp(@PathVariable("id") String id, @PathVariable("teamId") String teamId) {
        log.info("Assign team [%s] to the mvp [%s]", id, teamId);
        Mvp mvp;
        try {
            mvp = mvpService.assignTeamOfMvp(id, Integer.parseInt(teamId));
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Mvp>(mvp, HttpStatus.OK);
    }

    @ApiOperation(value = "Unassign team")
    @PutMapping(value = "/{id}/unassignTeam")
    public ResponseEntity<?> unassignTeamToMvp(@PathVariable("id") String id) {
        log.info("Unassign team to the mvp [%s]", id);
        Mvp mvp;
        try {
            mvp = mvpService.unassignTeamOfMvp(id);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Mvp>(mvp, HttpStatus.OK);
    }
}
