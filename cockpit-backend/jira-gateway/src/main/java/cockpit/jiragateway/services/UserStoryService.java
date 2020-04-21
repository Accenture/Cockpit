package cockpit.jiragateway.services;

import cockpit.cockpitcore.domaine.db.Sprint;
import cockpit.cockpitcore.domaine.db.UserStory;
import cockpit.cockpitcore.domaine.jira.History;
import cockpit.cockpitcore.domaine.jira.Issue;
import cockpit.cockpitcore.domaine.jira.Issues;
import cockpit.cockpitcore.domaine.jira.Item;
import cockpit.jiragateway.exceptions.JiraException;
import cockpit.jiragateway.configurations.UrlConfiguration;
import cockpit.cockpitcore.repository.UserStoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserStoryService {

    private UrlConfiguration urlConfiguration;
    private UserStoryRepository userStoryRepository;
    private MvpService mvpService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStoryService.class);
    public static final String DONE = "Done";
    public static final String TO_DO = "Do";
    public static final String DEV_IN_PROGRESS = "Progress";
    private List<UserStory> stories = null;

    @Autowired
    public UserStoryService(
            UrlConfiguration urlConfiguration,
            UserStoryRepository userStoryRepository,
            MvpService mvpService
    ){
        this.urlConfiguration = urlConfiguration;
        this.userStoryRepository = userStoryRepository;
        this.mvpService = mvpService;
    }


    public List<UserStory> updateUserStoryInDBForASprintFromJira(Sprint sprint, String urlIssues) throws JiraException {

        String sprintId = String.valueOf(sprint.getJiraSprintId());
        String jql = "Sprint=" + sprintId + " AND issuetype=Story&expand=changelog";
        String url = urlIssues + jql;
        ResponseEntity<Issues> result = (ResponseEntity<Issues>) urlConfiguration.callJira(url, Issues.class.getName());
        List<Issue> issueList = (result.getBody().getIssues());
        if (result.getStatusCode().is2xxSuccessful()) {
            return getUserStories(sprint, issueList);
        }
        return stories;
    }

    private void clearDBFromDifferenceInUserStories(List<Issue> issueList){

        if(!issueList.isEmpty()){
            List<String> newIssues = issueList.stream().map(Issue::getKey).collect(Collectors.toList());
            userStoryRepository.deleteAllByIssueKeyNotIn(newIssues);
        }

    }

    private List<UserStory> getUserStories(Sprint sprint, List<Issue> issueList) {
        stories = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        for (Issue issue : Optional.ofNullable(issueList).orElse(Collections.emptyList())) {
            UserStory userStory;

            Optional<UserStory> optionalUserStory = userStoryRepository.findByJiraIssueId(Integer.parseInt(issue.getId()));

            userStory = optionalUserStory.orElseGet(UserStory::new);

            userStory.setJiraIssueId(Integer.parseInt(issue.getId()));
            userStory.setIssueKey(issue.getKey());
            if (sprint != null) {
                userStory.setSprint(sprint);
            }

            if (issue.getFields() != null) {
                userStory.setSummary(issue.getFields().getSummary());
                userStory.setDescription("");
                userStory.setMvp(mvpService.getMvpByJiraProjectId(issue.getFields().getProject().getId()));
                userStory.setPriority((issue.getFields().getPriority() == null) ? "N/A" : issue.getFields().getPriority().getName());
                userStory.setEstimation((issue.getFields().getCustomfield10043() == null) ? 0 : (int) issue.getFields().getCustomfield10043());
                try {
                    userStory.setCreatedDate((issue.getFields().getCreated() == null) ? null : dateFormat.parse(issue.getFields().getCreated()));
                    if (issue.getFields().getCustomfield10070() != null) {
                        userStory.setStartDate(dateFormat.parse(issue.getFields().getCustomfield10070().toString()));
                    } else {
                        updateStoryStartDate(dateFormat, issue, userStory);
                    }
                } catch (ParseException e) {
                    LOGGER.error("Exception thrown when parsing updateDate for UserStory Status");
                }
                updateDoneDate(issue, userStory);
            }
            updateStoryStartDate(dateFormat, issue, userStory);
            stories.add(userStory);
            userStoryRepository.save(userStory);
        }
        return stories;
    }

    private void updateStoryStartDate(DateFormat dateFormat, Issue issue, UserStory userStory) {
        if (issue.getChangelog() != null) {
            if (issue.getChangelog().getHistories() != null) {
                List<History> historyList = (issue.getChangelog().getHistories());
                for (History history : Optional.ofNullable(historyList).orElse(Collections.emptyList())) {
                    for (Item item : Optional.ofNullable(history.getItems()).orElse(Collections.emptyList())) {
                        if (item.getFromString() != null && item.getToString() != null) {
                            if (item.getFromString().toString().matches("(?i:.*" + TO_DO + ".*)") && item.getToString().toString().matches("(?i:.*" + DEV_IN_PROGRESS + ".*)")) {
                                try {
                                    userStory.setStartDate(dateFormat.parse(history.getCreated()));
                                } catch (ParseException e) {
                                    LOGGER.error("Exception thrown when parsing creation date", e);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void updateDoneDate(Issue issue, UserStory userStory) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        if (issue.getFields().getStatus() != null) {
            updateDoneDateIfStatusNotNull(issue, userStory, dateFormat);

        }
    }

    private void updateDoneDateIfStatusNotNull(Issue issue, UserStory userStory, DateFormat dateFormat) {
        userStory.setStatus(issue.getFields().getStatus().getName());
        if (issue.getFields().getStatus().getName() != null && issue.getFields().getStatus().getName().equals(DONE)) {
            if (issue.getFields().getResolutiondate() != null) {
                updateDateFromResolutionDateField(issue, userStory, dateFormat);
            } else if (issue.getFields().getAdditionalProperties() != null) {
                updateDateFromAdditionalProperties(issue, userStory, dateFormat);
            }
        }
    }

    private void updateDateFromResolutionDateField(Issue issue, UserStory userStory, DateFormat dateFormat) {
        try {
            userStory.setDoneDate(dateFormat.parse(issue.getFields().getResolutiondate().toString()));
        } catch (ParseException e) {
            LOGGER.error("Unable to parse date for issue" + issue.getId() + " with date " + issue.getFields().getResolutiondate(), e);
        }
    }

    private void updateDateFromAdditionalProperties(Issue issue, UserStory userStory, DateFormat dateFormat) {
        Iterator it = issue.getFields().getAdditionalProperties().entrySet().iterator();
        try {
            if (it.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
                String resolutionDate = entry.getValue();
                if (resolutionDate != null)
                    userStory.setDoneDate(dateFormat.parse(resolutionDate));
            }
        } catch (NoSuchElementException | ParseException e) {
            LOGGER.error("Unable to parse date from additional properties of the returned issue", e);
        }
    }

    public void cleanUserStoriesNotLongerExists(String urlIssues) throws JiraException {
        List<Issue> issueList = new ArrayList<Issue>();
        List<UserStory> userStoryList = null;
        int maxResults = 100;
        int startAt = 0;
        int totalValues = 0;
        int i = 1;
        while (totalValues >= startAt) {
            String url = String.format(urlIssues, maxResults, startAt);
            ResponseEntity<Issues> result = (ResponseEntity<Issues>) urlConfiguration.callJira(url, Issues.class.getName());
            if (result.getStatusCode().is2xxSuccessful()) {
                totalValues = result.getBody().getTotal();
                startAt = (maxResults * i) + 1;
                issueList.addAll(result.getBody().getIssues());
                i++;
            } else {
                throw new JiraException("Unable to get Issues From Jira");
            }
        }
        try {
            clearDBFromDifferenceInUserStories(issueList);
        }
        catch (Exception e) {
            LOGGER.error("UserStory - Unable to clean user stories that no longer exists in JIRA");
            LOGGER.error(e.getMessage());
        }
    }
}
