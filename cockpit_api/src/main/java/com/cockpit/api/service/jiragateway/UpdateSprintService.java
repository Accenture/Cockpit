package com.cockpit.api.service.jiragateway;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dao.UserStory;
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
public class UpdateSprintService {
    Logger log = LoggerFactory.getLogger(UpdateSprintService.class);
    private List<Sprint> sprintsToRemove = new ArrayList<>();
    private final SprintRepository sprintRepository;
    private final UserStoryRepository userStoryRepository;
    private final JiraRepository jiraRepository;
    private final JiraApiConfiguration configurationJiraAPIs;
    final UserStoryService userStoryService;

    @Autowired
    public UpdateSprintService(JiraRepository jiraRepository, SprintRepository sprintRepository,
                              UserStoryRepository userStoryRepository,
                              UserStoryService userStoryService, JiraApiConfiguration configurationJiraAPIs) {
        this.jiraRepository = jiraRepository;
        this.userStoryRepository = userStoryRepository;
        this.sprintRepository = sprintRepository;
        this.userStoryService = userStoryService;
        this.configurationJiraAPIs = configurationJiraAPIs;
    }

    @Value("${spring.jira.urlSprints}")
    private String urlSprints;

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = ONE_HOUR)
    public void updateSprintsFromJira() {
        try {
            List<Jira> jiraList = jiraRepository.findAllByOrderById();
            for (Jira jira : Optional.ofNullable(jiraList).orElse(Collections.emptyList())) {
                if (jira.getBoardId() != null) {
                    List<SprintJira> sprintList = getSprintsFromJira(jira.getBoardId(), urlSprints);
                    if (sprintList != null && !sprintList.isEmpty()) {
                        updateSprintsInDB(sprintList, jira);
                    }
                    reorderSprintNumberPerJira(jira);
                }
            }
        } catch (Exception e) {
        }
    }

    @Scheduled(initialDelay = 90 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    public void setTotalNbOfUserStoryForEachSprintOfEachProject() {
        List<Jira> jiraProjectList = jiraRepository.findAllByOrderById();
        for (Jira jira : jiraProjectList) {
            List<Sprint> sprintList = sprintRepository.findByJiraOrderBySprintNumber(jira);
            int totalNumberOfUserStoriesUntilCurrentSprint = 0;
            if (sprintList.size() > 0) {
                totalNumberOfUserStoriesUntilCurrentSprint = userStoryRepository
                        .countUserStoriesByJiraAndCreationDateBefore(jira,
                                sprintList.get(0).getSprintStartDate());
            }
            for (Sprint sprint :
                    sprintList) {
                Date sprintStartDate = sprint.getSprintStartDate();
                Date sprintEndDate = sprint.getSprintEndDate();
                Date sprintCompletionDate = sprint.getSprintCompleteDate();
                if (sprintStartDate != null &&
                        sprintEndDate != null) {
                    if (sprintCompletionDate != null) {
                        sprintEndDate = sprintCompletionDate;
                    }
                    int nbUserStoriesCreatedDuringCurrentSprint =
                            userStoryRepository
                                    .countUserStoriesByJiraAndCreationDateGreaterThanAndCreationDateLessThanEqual
                                            (jira, sprintStartDate, sprintEndDate);
                    totalNumberOfUserStoriesUntilCurrentSprint +=
                            nbUserStoriesCreatedDuringCurrentSprint;
                    sprint.setTotalNbUs(totalNumberOfUserStoriesUntilCurrentSprint);
                    sprintRepository.save(sprint);
                }
            }
        }
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

    public void reorderSprintNumberPerJira(Jira jira) {
        List<Sprint> mvpSprints;
        mvpSprints = sprintRepository.findByJiraOrderBySprintNumber(jira);

        if (mvpSprints != null || !mvpSprints.isEmpty()) {
            int count = 1;
            for (Sprint sprint : mvpSprints) {
                sprint.setSprintNumber(count);
                sprintRepository.save(sprint);
                count++;
            }
        }
    }

    public void updateSprintsInDB(List<SprintJira> sprintJiraList, Jira jira) {
        int sprintNumber = 1;
        List<Sprint> sprints = sprintRepository.findByJiraOrderBySprintNumber(jira);
        for (SprintJira sprintJira : Optional.ofNullable(sprintJiraList).orElse(Collections.emptyList())) {
            Sprint sprintExist = sprintRepository.findByJiraSprintId(sprintJira.getId());
            if (sprintJira.getOriginBoardId() == jira.getBoardId()) {
                Sprint newSprint = new Sprint();
                if (sprintExist != null) {
                    newSprint.setId(sprintExist.getId());
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
                sprintRepository.save(newSprint);
            } else if (sprintExist != null && sprintJira.getId() == sprintExist.getJiraSprintId()) {
                if (sprintJira.getOriginBoardId() != jira.getBoardId()) {
                    sprintsToRemove.add(sprintExist);
                }
            }
            sprintNumber++;
        }
        getSprintsToRemove(sprintJiraList, sprints);
        cleanWrongSprintFromJira(sprintsToRemove);
    }
    public void cleanWrongSprintFromJira(List<Sprint> sprints) {
        if (sprints != null && !sprints.isEmpty()) {
            for (Sprint sprint : sprints) {

                sprintRepository.delete(sprint);
            }
            sprints.clear();
        }
    }

    public void getSprintsToRemove(List<SprintJira> sprintListJira, List<Sprint> sprintList) {
        for (Sprint sprint : sprintList) {
            if (sprintListJira.stream().filter(sprintJira -> sprint.getJiraSprintId() == sprintJira.getId()).count() == 0) {
                sprintsToRemove.add(sprint);
            }
        }
    }
}
