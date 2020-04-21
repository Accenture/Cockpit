package cockpit.jiragateway.components;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.jira.JiraBoard;
import cockpit.jiragateway.exceptions.JiraException;
import cockpit.jiragateway.services.MvpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

import static javax.management.timer.Timer.*;

@Component
public class UpdateMVP {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateMVP.class);


    @Value("${spring.application.jira.urlProjects}")
    private String urlProjects;

    @Value("${spring.application.jira.urlBoards}")
    private String urlBoards;

    @Value("${spring.application.jira.urlIssues}")
    private String urlIssues;


    private MvpService mvpService;

    @Autowired
    public UpdateMVP (MvpService mvpService) {
        this.mvpService = mvpService;
    }

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 20 * ONE_SECOND)
    public void getProjectsIdFromJira() {
        LOGGER.info("MVP - Start getProjectsIdFromJira: Thread : " + Thread.currentThread().getName());
        try {
            mvpService.updateProjectIdInDBFromJira(urlProjects);
        } catch (JiraException e) {
            LOGGER.error("Exception thrown when getting updating the projectId", e);
        }
        LOGGER.info("MVP - End   getProjectsIdFromJira");
    }

    @Scheduled(initialDelay = 40 * ONE_SECOND, fixedDelay = 2 * ONE_HOUR)
    public void updateBoardIdInMvp() {

        LOGGER.info("MVP - Start updateBoardIdInMvp Thread : " + Thread.currentThread().getName());
        List<JiraBoard> jiraBoards = null;
        try {
            jiraBoards = mvpService.updateBoardIdInMVP(urlBoards);
        } catch (JiraException e) {
            LOGGER.error("Exception thrown when getting updating the boardsId", e);
        }
        LOGGER.info("MVP - End   updateBoardIdInMvp  for {} Boards", (jiraBoards != null) ? jiraBoards.size() : "0");
    }

    @Scheduled(initialDelay = 3 * ONE_MINUTE, fixedDelay = 40 * ONE_MINUTE)
    public void updateNumberStoriesForMvps() {

        LOGGER.info("MVP - Start updateNumberStoriesForMvps Thread : " + Thread.currentThread().getName());
        List<Mvp> mvpList = null;
        try {
            mvpList = mvpService.updateNumberOfStoriesForAllMvp(urlIssues);
        } catch (JiraException e) {
            LOGGER.error("Exception thrown when getting updating the number of Stories", e);
        }
        LOGGER.info("MVP - End   updateNumberStoriesForMvps  for {} MVPS", (mvpList != null) ? mvpList.size() : "0");
    }

    @Scheduled(initialDelay = 2 * ONE_MINUTE, fixedDelay = 40 * ONE_MINUTE)
    public void updateBugsCountOfAnMvp() {

        LOGGER.info("MVP - Start updateBugsCountOfAnMvp Thread : " + Thread.currentThread().getName());
        List<Mvp> mvpList = null;
        try {
            mvpList = mvpService.updateBugsCountOfMvps(urlIssues);
        } catch (JiraException e) {
            LOGGER.error("Exception thrown when updating bugs count", e);
        }
        LOGGER.info("MVP - End   updateBugsCountOfAnMvp  for {} MVPs", (mvpList != null) ? mvpList.size() : "0");
    }

    @Scheduled(initialDelay = ONE_MINUTE, fixedDelay = 40 * ONE_MINUTE)
    public void updateTimeToFix() {
        List<Mvp> mvpList = null;
        LOGGER.info("MVP - Start updating time to fix Thread : " + Thread.currentThread().getName());
        try {
            mvpService.updateTimeToFix();
        } catch (JiraException e) {
            LOGGER.error("Exception thrown when updating time to fix ", e);
        }
        LOGGER.info("MVP - End   updating time to fix for {} MVPS", (mvpList != null) ? mvpList.size() : "0");
    }

    @Scheduled(initialDelay = ONE_MINUTE, fixedDelay = 40 * ONE_MINUTE)
    public void updateTimeToDetect() {
        List<Mvp> mvpList = null;
        LOGGER.info("MVP - Start updating time to detect Thread : " + Thread.currentThread().getName());
        try {
            mvpService.updateTimeToDetect();
        } catch (JiraException e) {
            LOGGER.error("Exception thrown when updating time to detect ", e);
        }
        LOGGER.info("MVP - End   updating time to detect for {} MVPS", (mvpList != null) ? mvpList.size() : "0");
    }

    @Scheduled(initialDelay = ONE_MINUTE, fixedDelay = 30 * ONE_MINUTE)
    public void updateActualSprintNumberForMvp() {
        LOGGER.info("MVP - Start updateActualSprintNumberForMvp Thread : " + Thread.currentThread().getName());
        List<Mvp> mvpList;
        try {
            mvpService.updateActualSprintNumberForMvps();
        } catch (JiraException e) {
            LOGGER.error("Exception thrown when getting updating the actual sprintNumber for an MVP", e);
        }
        LOGGER.info("MVP - End   updateActualSprintNumberForMvp");
    }

}
