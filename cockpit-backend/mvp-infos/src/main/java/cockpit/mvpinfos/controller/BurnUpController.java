package cockpit.mvpinfos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.dto.ChartData;
import cockpit.mvpinfos.service.BurnUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/1.0/burnup")
@Api(value = "/api/1.0/burnup")
@CrossOrigin
@Slf4j
public class BurnUpController {

    @Autowired
    private BurnUpService burnUpService;

    @ApiOperation(value = "Get all data for chart generation")
    @RequestMapping(value = "/{jiraProjectKey}", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ChartData> getChartDataForSprints(@PathVariable("jiraProjectKey") String jiraProjectKey) {
        return burnUpService.getChartData(jiraProjectKey);
    }
}
