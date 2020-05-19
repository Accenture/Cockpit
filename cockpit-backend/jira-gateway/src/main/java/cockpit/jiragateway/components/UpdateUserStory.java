package cockpit.jiragateway.components;

import cockpit.cockpitcore.domaine.db.Sprint;
import cockpit.jiragateway.exceptions.JiraException;
import cockpit.jiragateway.services.SprintService;
import cockpit.jiragateway.services.UserStoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static javax.management.timer.Timer.*;

@Component
public class UpdateUserStory {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateUserStory.class);

    @Value("${spring.application.jira.urlIssues}")
    private String urlIssues;

    @Value("${spring.application.jira.urlAllIssues}")
    private String urlAllIssues;

    private UserStoryService userStoryService;
    private SprintService sprintService;

    @Autowired
    public UpdateUserStory (
            UserStoryService userStoryService,
            SprintService sprintService
    ) {
        this.userStoryService = userStoryService;
        this.sprintService = sprintService;
    }

    @Scheduled(initialDelay = 9 * ONE_SECOND, fixedDelay = ONE_HOUR)
    public void updateUserStoryInDBFromJira() {
        LOGGER.info("UserStory - Start updateUserStoryInDBFromJira Thread : " + Thread.currentThread().getName());
        List<Sprint> sprintList;
        try {
            sprintList = sprintService.findAllSprintsInDB();

            for (Sprint sprint : Optional.ofNullable(sprintList).orElse(Collections.emptyList())) {
                userStoryService.updateUserStoryInDBForASprintFromJira(sprint, urlIssues);
            }
        } catch (JiraException e) {
            LOGGER.error("Unable to find sprints in the database");
            LOGGER.error(e.getMessage());
        }


        LOGGER.info("UserStory - End   updateUserStoryInDBFromJira Thread : " + Thread.currentThread().getName());
    }

    @Scheduled(initialDelay = 28 * ONE_SECOND, fixedDelay = ONE_HOUR)
    public void cleaningUselessUSFromDB() {
        LOGGER.info("UserStory - Start cleaning useless user stories - Thread : " + Thread.currentThread().getName());
        try{
            userStoryService.cleanUserStoriesNotLongerExists(urlAllIssues);
        }
        catch (Exception e) {
            LOGGER.error("Exception thrown when trying to delete user stories in DB not present in JIRA");
            LOGGER.debug(e.getMessage());
        }
        LOGGER.info("UserStory - End cleaning useless user stories - Thread : " + Thread.currentThread().getName());
    }
}
