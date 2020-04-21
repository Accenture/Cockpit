package cockpit.mvpinfos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.Sprint;
import cockpit.mvpinfos.exception.ResourceNotFoundException;
import cockpit.mvpinfos.service.SprintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/1.0/sprint")
@Api(value = "/api/1.0/sprint")
@Slf4j
@CrossOrigin
public class SprintController {

    private SprintService sprintService;
    @Autowired
    public SprintController(SprintService sprintService) {
        this.sprintService =sprintService;
    }

    @ApiOperation(value = "Get all sprints")
    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Sprint> getAllSprints() {
        log.info("getAllSprints");
        return sprintService.findAllSprints();
    }

    @ApiOperation(value = "Get all sprints of a MVP")
    @RequestMapping(value = "/{mvp}/all", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Sprint> getAllSprintsByMVP(@PathVariable("mvp") Mvp mvp) {
        try {
            return sprintService.getSprintListByMvp(mvp);

        } catch (ResourceNotFoundException e) {
            log.error("getAllSprintsByMVP - Resource not found", e);
        }
        return new ArrayList<>();
    }

    @ApiOperation(value = "Get Sprint of mvp and sprint number")
    @RequestMapping(value = "/{mvpId}/{sprintNumber}", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sprint> getSprintByMvpAndSprintNumber(@PathVariable("mvpId") Mvp mvp, @PathVariable("sprintNumber") int sprintNumber) {
        log.info("getSprintByMvpAndSprintNumber - returned sprint of mvp: " + mvp.getId() + " and sprintNumber: " + sprintNumber);
        Sprint sprint = sprintService.findSprintByMvpBySprintNumber(mvp, sprintNumber);
        if (sprint == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(sprint);

    }

    @ApiOperation("Update sprint fields")
    @PutMapping(value = "/{mvp}/{sprintNumber}")
    public ResponseEntity<?> updateSprintDetails(@PathVariable("mvp") Mvp mvp,
                                                 @PathVariable("sprintNumber") int sprintNumber,
                                                 @RequestBody Sprint newSprint) {

        log.info("updateSprintDetails - updating sprint[mvp: " + mvp.getId() + ", sprintNumber: " + sprintNumber + "] ");
        Sprint currentSprint = sprintService.findSprintByMvpBySprintNumber(mvp, sprintNumber);
        if (currentSprint == null) {
            log.info("The required sprint does not exist");
            return ResponseEntity.notFound().build();
        }
        Sprint updatedSprint = sprintService.updateProperties(currentSprint, newSprint);
        sprintService.updateSprint(updatedSprint);
        log.info("updateSprintDetails - sprint updated");
        return ResponseEntity.noContent().build();
    }

}
