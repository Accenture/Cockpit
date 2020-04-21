package cockpit.mvpinfos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cockpit.cockpitcore.domaine.db.Bug;
import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.mvpinfos.service.BugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/1.0/bug")
@Api(value = "/api/1.0/bug")
@CrossOrigin
@Slf4j
public class BugController {

    private BugService bugService;

    @Autowired
    public BugController (BugService bugService){
        this.bugService = bugService;
    }

    @ApiOperation(value = "Get all bugs for all MVPs")
    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Bug> getAllBugs() {
        return bugService.findAll();
    }

    @ApiOperation(value = "Get bugs of a MVP")
    @GetMapping(value = "/{mvp}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Bug> getBugsByMvpId(@PathVariable("mvp") Mvp mvp) {
        log.info("getBugsByMvpId - returns list of bugs for mvp[id:" + mvp.getId() + "]");
        return bugService.getBugsForAnMvp(mvp);
    }
}
