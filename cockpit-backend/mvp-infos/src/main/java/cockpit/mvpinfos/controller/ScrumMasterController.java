package cockpit.mvpinfos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/1.0/scrumMaster")
@Api(value = "${/api/1.0/scrumMaster")
@CrossOrigin
@Slf4j
public class ScrumMasterController {

    @ApiOperation(value = "Allow ScrumMaster Rights")
    @RequestMapping(value = "/isScrumMaster", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean isScrumMaster() {
        // Add auth part here to verify if a user is Scrum master or not
        return true;
    }

}
