package cockpit.jiragateway.components;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.Sprint;
import cockpit.cockpitcore.domaine.jira.SprintJira;
import cockpit.jiragateway.exceptions.JiraException;
import cockpit.jiragateway.services.MvpService;
import cockpit.jiragateway.services.SprintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static javax.management.timer.Timer.*;

@Component
public class UpdateSprint {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateSprint.class);

    @Value("${spring.application.jira.credentials}")
    private String jiraCredentials;

    @Value("${spring.application.jira.urlSprints}")
    private String urlSprints;

    private SprintService sprintService;
    private MvpService mvpService;

    @Autowired
    public UpdateSprint (
            SprintService sprintService,
            MvpService mvpService
    ) {
        this.sprintService = sprintService;
        this.mvpService = mvpService;
    }

    @Scheduled(initialDelay = 6 * ONE_SECOND, fixedDelay = ONE_HOUR)
    public void updateSprintsFromJira() {
        LOGGER.info("SPRINT - Start updateSprintsFromJira Thread : " + Thread.currentThread().getName());
        List<Mvp> mvpList;
        try {
            mvpList = mvpService.getActiveProjects();
            for (Mvp mvp : Optional.ofNullable(mvpList).orElse(Collections.emptyList())) {
                if (mvp.getJiraBoardId() != 0) {
                    List<SprintJira> sprintList = sprintService.getSprintsFromJira(mvp.getJiraBoardId(), urlSprints);
                    if (sprintList != null && !sprintList.isEmpty()) {
                        sprintService.updateSprintsInDB(sprintList, mvp);
                    }
                    sprintService.reorderSprintNumberPerMVP(mvp);
                    LOGGER.info("SPRINT - All Sprints number for MVP with id : " + mvp.getId() + " has been reordered");
                }
            }
            List<Sprint> sprintsToRemove = sprintService.getSprintsToRemove();
            sprintService.cleanWrongSprintFromMvp(sprintsToRemove);
        } catch (JiraException e) {
            LOGGER.error("Exception thrown when getting & updating the sprint values from Jira", e);
        }
        LOGGER.info("SPRINT - End   updateSprintsFromJira Thread : " + Thread.currentThread().getName());
    }

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = ONE_DAY)
    @Transactional
    public void updateTotalNbOfUserStory() throws JiraException {
        LOGGER.info("SPRINT - Start updateTotalNbOfUserStory Thread : " + Thread.currentThread().getName());
        List<Mvp> mvpList;
        mvpList = mvpService.getActiveProjects();
        sprintService.setTotalNbOfUserStories(mvpList);
    }

}
