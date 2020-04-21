package cockpit.mvpinfos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.TechnicalDebt;
import cockpit.mvpinfos.service.TechnicalDebtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@RequestMapping("/api/1.0/techDebt")
@Api(value = "/api/1.0/techDebt")
@Slf4j
public class TechnicalDebtController {

    private TechnicalDebtService technicalDebtService;
    @Autowired
    public TechnicalDebtController(TechnicalDebtService technicalDebtService) {
        this.technicalDebtService = technicalDebtService;
    }

    @ApiOperation(value = "Get all Technical Debt for All MVP")
    @RequestMapping(value = "/all", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TechnicalDebt> getAllTechnicalDebt() {
        return technicalDebtService.getAllTechnicalDebts();
    }

    @ApiOperation(value = "Get the technical debt of a MVP")
    @RequestMapping(value = "/{mvp}", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public TechnicalDebt getByMvpId(@PathVariable("mvp") Mvp mvp) {
        TechnicalDebt technicalDebt;
        technicalDebt = technicalDebtService.getTechnicalDebtForAnMvp(mvp);
        if (technicalDebt == null) {
            log.error("TechnicalDebtService getByMvpId - mvp with the id : " + mvp.getId() + " is not found");
        }
        return technicalDebt;
    }
}
