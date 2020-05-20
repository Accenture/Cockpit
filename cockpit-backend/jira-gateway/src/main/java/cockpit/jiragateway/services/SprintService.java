package cockpit.jiragateway.services;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.Sprint;
import cockpit.cockpitcore.domaine.db.UserStory;
import cockpit.cockpitcore.domaine.jira.SprintHeaders;
import cockpit.cockpitcore.domaine.jira.SprintJira;
import cockpit.cockpitcore.repository.UserStoryRepository;
import cockpit.jiragateway.exceptions.JiraException;
import cockpit.jiragateway.configurations.UrlConfiguration;
import cockpit.cockpitcore.repository.SprintRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SprintService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintService.class);

    private UrlConfiguration urlConfiguration;
    private SprintRepository sprintRepository;
    private UserStoryRepository userStoryRepository;

    @Autowired
    public SprintService(
            UrlConfiguration urlConfiguration,
            SprintRepository sprintRepository,
            UserStoryRepository userStoryRepository
    ){
        this.urlConfiguration = urlConfiguration;
        this.sprintRepository = sprintRepository;
        this.userStoryRepository = userStoryRepository;
    }

    private  List<Sprint> sprintsToRemove  = new ArrayList<>();

    public List<SprintJira> getSprintsFromJira(int boardId, String urlSprints) throws JiraException {
        ResponseEntity<SprintHeaders> result = (ResponseEntity<SprintHeaders>) urlConfiguration.callJira(urlSprints + boardId + "/sprint", SprintHeaders.class.getName());
        List<SprintJira> newSprintsList = null;
        if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            newSprintsList = (result.getBody().getValues());
        }
        return newSprintsList;
    }

    public void updateSprintsInDB(List<SprintJira> sprintJiraList, Mvp mvp) {
        int sprintNumber = 0;
        for (SprintJira sprintJira : Optional.ofNullable(sprintJiraList).orElse(Collections.emptyList())) {
            Sprint sprintExist = sprintRepository.findByJiraSprintId(sprintJira.getId());
            if (sprintExist == null && sprintJira.getOriginBoardId() == mvp.getJiraBoardId()) {
                Sprint newSprint = new Sprint();
                newSprint.setJiraSprintId(sprintJira.getId());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                if (sprintJira.getStartDate() != null && sprintJira.getEndDate() != null) {
                    try {
                        newSprint.setSprintStartDate(dateFormat.parse(sprintJira.getStartDate()));
                        newSprint.setSprintEndDate(dateFormat.parse(sprintJira.getEndDate()));
                    } catch (ParseException e) {
                        String message = "ERROR : Unable to parse either startDate {} or End date {} of Sprint";
                        LOGGER.warn(message, sprintJira.getStartDate(), sprintJira.getEndDate());
                    }
                    newSprint.setMvp(mvp);
                    newSprint.setSprintNumber(sprintNumber);
                    sprintRepository.save(newSprint);
                }
            }
            else if (sprintExist != null && sprintJira.getId() == sprintExist.getJiraSprintId()) {
                if(sprintJira.getOriginBoardId() != mvp.getJiraBoardId()) {
                    sprintsToRemove.add(sprintExist);
                }
            }
            sprintNumber++;
        }

        String message = "DEBUG : Updated  {} sprints for MVP {} ";
        LOGGER.debug(message, sprintNumber, mvp.getId());
    }

    public void reorderSprintNumberPerMVP(Mvp mvp) throws JiraException {
        List<Sprint> mvpSprints;
        mvpSprints = sprintRepository.findByMvp(mvp);

        if(mvpSprints != null || !mvpSprints.isEmpty()) {
            int count = 0;
            for(Sprint sprint :  mvpSprints) {
                sprint.setSprintNumber(count);
                sprintRepository.save(sprint);
                count++;
            }
        }
    }

    public List<Sprint> findAllSprintsInDB() throws JiraException {
        List<Sprint> sprintList = sprintRepository.findAll();
        if (sprintList == null || sprintList.isEmpty()) {
            String message = "Sprint - No Sprints found in DB";
            throw new JiraException(message);
        }
        return sprintList;
    }

    public int findSprintNumberForToday(Mvp mvp) {
        Date today = new Date();
        Sprint sprint = sprintRepository.findTopBySprintStartDateLessThanEqualAndMvpEqualsOrderBySprintNumberDesc(today, mvp);
        int sprintId = 0;
        if (sprint != null) {
            sprintId = sprint.getSprintNumber();
        }
        return sprintId;
    }

    public void cleanWrongSprintFromMvp(List<Sprint> sprints) {
        if(sprints != null && !sprints.isEmpty()) {
            for(Sprint sprint : sprints) {
                LOGGER.info("Sprint number : "+sprint.getSprintNumber()+" from MVP with id : "+sprint.getMvp().getId()+" has been removed because of wrong BoardID");
                sprintRepository.delete(sprint);
            }
            sprints.clear();
        }
    }

    public List<Sprint> getSprintsToRemove() {
        return sprintsToRemove;
    }

    public void setTotalNbOfUserStories(List<Mvp> mvpList) {
        for (Mvp mvp : Optional.ofNullable(mvpList).orElse(Collections.emptyList())) {
            if (mvp.getJiraBoardId() != 0) {
                ArrayList<Sprint> sprintList = new ArrayList<>(mvp.getSprints());
                if (!sprintList.isEmpty()) {
                    Sprint currentSprint = sprintList.stream().filter(sprint ->  sprint.getSprintNumber()==mvp.getCurrentSprint()).findFirst().orElse(null);
                    if (currentSprint!=null) {
                        List<UserStory> userStoriesList = userStoryRepository.findAllByMvp(mvp);
                        currentSprint.setTotalNbUs(userStoriesList.size());
                        sprintRepository.save(currentSprint);
                    }

                }
            }
            LOGGER.info("SPRINT - All total user story numbers with id : " + mvp.getId() + " has been reordered");
        }
    }

}
