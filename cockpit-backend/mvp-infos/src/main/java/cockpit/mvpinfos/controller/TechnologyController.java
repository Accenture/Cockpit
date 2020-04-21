package cockpit.mvpinfos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cockpit.cockpitcore.domaine.db.Technology;
import cockpit.mvpinfos.service.TechnologyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/1.0/technology")
@Api(value = "/api/1.0/technology")
@CrossOrigin
public class TechnologyController {

    private final Logger logger = LoggerFactory.getLogger(TechnologyController.class);

    private TechnologyService technologyService;
    @Autowired
    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @ApiOperation(value = "Get all technologies")
    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Technology> getAllTechnologies() {
        logger.info("Get All technologies");
        return technologyService.getAllTechnologies();
    }

}
