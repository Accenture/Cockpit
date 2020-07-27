package com.cockpit.api.service.jiragateway;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.jira.SprintHeaders;
import com.cockpit.api.model.dto.jira.SprintJira;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.repository.SprintRepository;
import com.cockpit.api.repository.UserStoryRepository;
import com.cockpit.api.service.UserStoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static javax.management.timer.Timer.*;

@Configuration
@EnableScheduling
@Service
@Transactional
public class UpdateSprint {
    Logger log = LoggerFactory.getLogger(UpdateSprint.class);
    private List<Sprint> sprintsToRemove = new ArrayList<>();
    private final SprintRepository sprintRepository;
    private final UserStoryRepository userStoryRepository;
    private final JiraRepository jiraRepository;
    private final JiraApiService configurationJiraAPIs;
    final UserStoryService userStoryService;

    @Autowired
    public UpdateSprint(JiraRepository jiraRepository, SprintRepository sprintRepository,
                        UserStoryRepository userStoryRepository,
                        UserStoryService userStoryService, JiraApiService configurationJiraAPIs) {
        this.jiraRepository = jiraRepository;
        this.userStoryRepository = userStoryRepository;
        this.sprintRepository = sprintRepository;
        this.userStoryService = userStoryService;
        this.configurationJiraAPIs = configurationJiraAPIs;
    }

    @Value("${spring.jira.urlSprints}")
    private String urlSprints;

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = ONE_HOUR)
    public void updateSprintsForEachProject() {
        log.info("Sprint - Start update sprints");
        try {
            List<Jira> jiraList = jiraRepository.findAllByOrderById();
            for (Jira jira : Optional.ofNullable(jiraList).orElse(Collections.emptyList())) {
                if (jira.getBoardId() != null) {
                    List<SprintJira> sprintList = getSprintsFromJira(jira.getBoardId(), urlSprints);
                    if (sprintList != null && !sprintList.isEmpty()) {
                        updateSprintsInDB(sprintList, jira);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to update Sprints from Jira: {}", e.getMessage());
        }
        log.info("Sprint - End update sprints");
    }

    @Scheduled(initialDelay = 90 * ONE_SECOND, fixedDelay = ONE_HOUR)
    public void setTotalNbOfUserStoryForEachSprintOfEachProject() {
        log.info("Sprint - Start update TotalNbUserStory for each sprint");
        List<Jira> jiraProjectList = jiraRepository.findAllByOrderById();
        for (Jira jira : jiraProjectList) {
            List<Sprint> sprintList = sprintRepository.findByJiraOrderBySprintNumber(jira);
            int totalNumberOfUserStoriesUntilCurrentSprint = 0;
            if (!sprintList.isEmpty()) {
                totalNumberOfUserStoriesUntilCurrentSprint = userStoryRepository
                        .countUserStoriesByJiraAndCreationDateBefore(jira,
                                sprintList.get(0).getSprintStartDate());
            }
            for (Sprint sprint : sprintList) {
                Date sprintStartDate = sprint.getSprintStartDate();
                Date sprintEndDate = sprint.getSprintEndDate();
                Date sprintCompletionDate = sprint.getSprintCompleteDate();
                if (sprintStartDate != null &&
                        sprintEndDate != null) {
                    if (sprintCompletionDate != null) {
                        sprintEndDate = sprintCompletionDate;
                    }
                    int nbUserStoriesCreatedDuringCurrentSprint = userStoryRepository
                            .countUserStoriesByJiraAndCreationDateGreaterThanAndCreationDateLessThanEqual(jira, sprintStartDate, sprintEndDate);
                    totalNumberOfUserStoriesUntilCurrentSprint += nbUserStoriesCreatedDuringCurrentSprint;
                    sprint.setTotalNbUs(totalNumberOfUserStoriesUntilCurrentSprint);
                    sprintRepository.save(sprint);
                }
            }
        }
        log.info("Sprint - End update TotalNbUserStory for each sprint");
    }


    public List<SprintJira> getSprintsFromJira(int boardId, String urlSprints) throws Exception {
        ResponseEntity<SprintHeaders> result = (ResponseEntity<SprintHeaders>) configurationJiraAPIs.callJira(
                urlSprints + boardId + "/sprint", SprintHeaders.class.getName());
        List<SprintJira> newSprintsList = null;
        if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            newSprintsList = (result.getBody().getValues());
        }
        return newSprintsList;
    }

    public void updateSprintsInDB(List<SprintJira> sprintJiraList, Jira jira) {
        int sprintNumber = 1;
        List<Sprint> sprints = sprintRepository.findByJiraOrderBySprintNumber(jira);
        for (SprintJira sprintJira : Optional.ofNullable(sprintJiraList).orElse(Collections.emptyList())) {
            Sprint sprintExist = sprintRepository.findByJiraSprintId(sprintJira.getId());
            sprintRepository.save(setNewSprint(sprintExist, sprintJira, jira, sprintNumber));
            sprintNumber++;
        }
        getSprintsToRemove(sprintJiraList, sprints);
        cleanNotExistingSprintsFromJira(sprintsToRemove);
    }

    public Sprint setNewSprint(Sprint sprintExist, SprintJira sprintJira, Jira jira, int sprintNumber) {
        Sprint newSprint = new Sprint();
        if (sprintExist != null) {
            newSprint.setId(sprintExist.getId());
            if (sprintExist.getTeamMood() != null) {
                newSprint.setTeamMood(sprintExist.getTeamMood());
            }
            if (sprintExist.getTeamMotivation() != null) {
                newSprint.setTeamMotivation(sprintExist.getTeamMotivation());
            }
            if (sprintExist.getTeamConfidence() != null) {
                newSprint.setTeamConfidence(sprintExist.getTeamConfidence());
            }
        }
        newSprint.setJiraSprintId(sprintJira.getId());
        newSprint.setState(sprintJira.getState());
        newSprint.setJira(jira);
        newSprint.setSprintNumber(sprintNumber);
        if (newSprint.getState().equals("active")) {
            jira.setCurrentSprint(sprintNumber);
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        if (sprintJira.getStartDate() != null && sprintJira.getEndDate() != null) {
            try {
                newSprint.setSprintStartDate(dateFormat.parse(sprintJira.getStartDate()));
                newSprint.setSprintEndDate(dateFormat.parse(sprintJira.getEndDate()));
                if (sprintJira.getCompleteDate() != null) {
                    newSprint.setSprintCompleteDate(dateFormat.parse(sprintJira.getCompleteDate()));
                }
            } catch (Exception e) {
                log.error("ERROR : Unable to parse either startDate or End date of Sprint or Complete Date of Sprint");
            }
        }
        return newSprint;
    }


    public void cleanNotExistingSprintsFromJira(List<Sprint> sprints) {
        if (sprints != null && !sprints.isEmpty()) {
            for (Sprint sprint : sprints) {
                sprintRepository.delete(sprint);
            }
            sprints.clear();
        }
    }

    public void getSprintsToRemove(List<SprintJira> sprintListJira, List<Sprint> sprintListDb) {
        for (Sprint sprint : sprintListDb) {
            if (sprintListJira.stream().filter(sprintJira -> sprint.getJiraSprintId() == sprintJira.getId()).count() == 0) {
                sprintsToRemove.add(sprint);
            }
        }
    }
}
