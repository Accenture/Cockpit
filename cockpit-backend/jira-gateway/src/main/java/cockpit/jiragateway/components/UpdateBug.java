package cockpit.jiragateway.components;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.jiragateway.exceptions.JiraException;
import cockpit.jiragateway.services.BugService;
import cockpit.jiragateway.services.MvpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static javax.management.timer.Timer.ONE_HOUR;
import static javax.management.timer.Timer.ONE_MINUTE;

@Component
public class UpdateBug {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateBug.class);


    @Value("${spring.application.jira.urlIssues}")
    private String urlIssues;

    private BugService bugService;
    private MvpService mvpService;

    @Autowired
    public UpdateBug(
            BugService bugService,
            MvpService mvpService) {
        this.bugService = bugService;
        this.mvpService = mvpService;
    }

    @Scheduled(initialDelay = 4 * ONE_MINUTE, fixedDelay = ONE_HOUR)
    public void updateBugsInDBFromJira() {
        LOGGER.info("Bug - start updating bugs in db \n Thread: " + Thread.currentThread().getName());
        List<Mvp> mvps;
        try {
            mvps = mvpService.getActiveProjects();
            for (Mvp mvp : Optional.ofNullable(mvps).orElse(Collections.emptyList())) {
                bugService.updateBugsInDB(mvp, urlIssues);
            }
        } catch (JiraException e) {
            LOGGER.error("unable to get mvp list from the data base");
        }
        LOGGER.info("Bug - End updating bugs in db");

    }

}
