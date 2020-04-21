package cockpit.jiragateway.services;

import cockpit.cockpitcore.domaine.db.Bug;
import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.UserStory;
import cockpit.cockpitcore.domaine.jira.*;
import cockpit.jiragateway.exceptions.JiraException;
import cockpit.jiragateway.configurations.UrlConfiguration;
import cockpit.cockpitcore.repository.MvpRepository;
import cockpit.cockpitcore.repository.UserStoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MvpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MvpService.class);


    private UrlConfiguration urlConfiguration;
    private MvpRepository mvpRepository;
    private SprintService sprintService;
    private UserStoryRepository userStoryRepository;

    @Autowired
    public MvpService (
            UrlConfiguration urlConfiguration,
            MvpRepository mvpRepository,
            SprintService sprintService,
            UserStoryRepository userStoryRepository
    ) {
        this.urlConfiguration = urlConfiguration;
        this.mvpRepository = mvpRepository;
        this.sprintService = sprintService;
        this.userStoryRepository = userStoryRepository;
    }

    public List<Mvp> getActiveProjects() throws JiraException {
        List<Mvp> mvpList = mvpRepository.findAll();
        if (mvpList == null || mvpList.isEmpty()) {
            String message = "MVP - Unable to find All active MVP";
            throw new JiraException(message);
        }
        return mvpList.stream().filter(mvp -> mvp.getJiraProjectId() != 0).collect(Collectors.toList());
    }

    public void updateProjectIdInDBFromJira(String urlProjects) throws JiraException {

        ResponseEntity<Project[]> response = (ResponseEntity<Project[]>) urlConfiguration.callJira(urlProjects, Project[].class.getName());
        if (response != null && response.getStatusCode() != null && response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<Project> projectList = Arrays.asList(response.getBody());
            updateProjectId(projectList);
        } else {
            throw new JiraException("MVP - Response code : {}  while trying to update the projectID" + ((response == null || response.getStatusCode() == null) ? " N/A " : response.getStatusCode()));
        }
    }

    public List<JiraBoard> updateBoardIdInMVP(String urlBoards) throws JiraException {
        List<JiraBoard> foundJiraBoards = new ArrayList<>();
        List<JiraBoard> boardList = null;

        int maxResults = 50;
        int startAt = 0;
        int numberOfBoardIdReceived = 0;
        int totalBoardId;

        do {
            String url = String.format(urlBoards, maxResults, startAt);
            ResponseEntity<Board> result = (ResponseEntity<Board>) urlConfiguration.callJira(url, Board.class.getName());

            if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
                maxResults = result.getBody().getMaxResults();
                startAt = result.getBody().getStartAt();
                totalBoardId = result.getBody().getTotal();
                numberOfBoardIdReceived = startAt + maxResults;

                boardList = result.getBody().getValues();
                updateBoard(boardList);
            } else {
                throw new JiraException("MVP - Response code : while trying to update board for the MVP ");
            }
            startAt = startAt + maxResults;
            foundJiraBoards.addAll(boardList);
        }
        while(numberOfBoardIdReceived < totalBoardId);

        return foundJiraBoards;
    }

    public List<Mvp> updateNumberOfStoriesForAllMvp(String urlIssues) throws JiraException {
        List<Mvp> mvpList = this.getActiveProjects();
        for (Mvp mvp : Optional.ofNullable(mvpList).orElse(Collections.emptyList())) {
            String mvpId = String.valueOf(mvp.getJiraProjectId());
            String jql = "project=" + mvpId + " AND issuetype=Story" ;
            String url = urlIssues + jql;
            updateUserStories(mvp, url);
        }
        return mvpList;
    }

    public List<Mvp> updateBugsCountOfMvps(String urlIssues) throws JiraException {
        List<Mvp> mvpList = this.getActiveProjects();
        for (Mvp mvp : Optional.ofNullable(mvpList).orElse(Collections.emptyList())) {
            String jql = "project=" +"'"+ mvp.getId() +"'"+ " AND issuetype=bug";
            String url = urlIssues + jql;
            updateResolvedBugs(mvp, url);
            updateBugs(mvp, url);
        }
        return mvpList;
    }

    public void updateTimeToFix() throws JiraException{
        List<Mvp> mvpList = this.getActiveProjects();
        for (Mvp mvp : Optional.ofNullable(mvpList).orElse(Collections.emptyList())) {
            mvp.setTimeToFix(this.calculateTimeToFix(mvp));
            this.save(mvp);
        }
    }

    public BigDecimal calculateTimeToFix(Mvp mvp) {
        BigDecimal timeToFix;
        BigDecimal averageTimeToFix;
        long sum = 0;
        if (mvp != null && mvp.getBugs() != null && mvp.getBugsCount() != null && mvp.getBugsCount() != 0) {
            List<Bug> mvpImportantBugs = getImportantBugs(mvp.getBugs());
            if(!mvpImportantBugs.isEmpty()) {
                for (Bug bug : mvpImportantBugs) {
                    if (bug.getCreationDate() != null && bug.getResolutionDate() != null) {
                        long diffInMillis = Math.abs(bug.getResolutionDate().getTime() - bug.getCreationDate().getTime());
                        sum += diffInMillis;
                    }
                }
            }
            averageTimeToFix = new BigDecimal(sum).divide(new BigDecimal(mvp.getBugsCount()), 2, RoundingMode.HALF_UP);
            timeToFix = averageTimeToFix.divide(new BigDecimal(24 * 60 * 60 * 1000), 2, RoundingMode.HALF_UP);
        } else {
            timeToFix = new BigDecimal(0);
        }
        return timeToFix;
    }

    private List<Bug> getImportantBugs(List<Bug> mvpAllBugList) {
        List<Bug> importantBugs = new ArrayList<>();
        for(Bug bug : mvpAllBugList) {
            if(bug.getSeverity() != null) {
                if (bug.getSeverity().equals("Critical") || bug.getSeverity().equals("Major")) {
                    importantBugs.add(bug);
                }
            }
        }
        return importantBugs;
    }

    public void updateTimeToDetect() throws JiraException{
        List<Mvp> mvpList = this.getActiveProjects();
        for (Mvp mvp : Optional.ofNullable(mvpList).orElse(Collections.emptyList())) {
            mvp.setTimeToDetect(this.calculateTimeToDetect(mvp));
            this.save(mvp);
        }
    }

    public BigDecimal calculateTimeToDetect(Mvp mvp) {
        int bugsAttachedToAUserStory = 0;
        BigDecimal timeToDetect;
        long totalDifferenceInMillis = 0;
        if (mvp != null && mvp.getBugs() != null && mvp.getAllBugsCount() != 0) {
            List<Bug> importantBugs = getImportantBugs(mvp.getBugs());
            for (Bug bug : importantBugs) {
                if (bug.getRelatedUserStory() != null && bug.getCreationDate() != null) {
                    Date userStoryResolutionDate = getResolutionDateOfRelatedUserStory(bug.getRelatedUserStory());

                    if (userStoryResolutionDate != null) {
                        long diffInMillis = Math.abs(bug.getCreationDate().getTime() - userStoryResolutionDate.getTime());
                        totalDifferenceInMillis += diffInMillis;
                        bugsAttachedToAUserStory++;
                    }
                }
            }
            timeToDetect = calculateAverageTimeToDetect(bugsAttachedToAUserStory, totalDifferenceInMillis);

        } else {
            timeToDetect = new BigDecimal(0);
        }
        return timeToDetect;
    }

    private BigDecimal calculateAverageTimeToDetect(int bugsAttachedToAUserStory, long totalDifferenceInMillis) {
        BigDecimal averageTimeToDetect;
        BigDecimal timeToDetect;
        if (bugsAttachedToAUserStory > 0) {
            averageTimeToDetect = new BigDecimal(totalDifferenceInMillis).divide(new BigDecimal(bugsAttachedToAUserStory), 2, RoundingMode.HALF_UP);
            timeToDetect = averageTimeToDetect.divide(new BigDecimal(24 * 60 * 60 * 1000), 2, RoundingMode.HALF_UP);
        } else {
            timeToDetect = new BigDecimal(-1);
        }
        return timeToDetect;
    }

    public void updateActualSprintNumberForMvps() throws JiraException{
        List<Mvp> mvpList = this.getActiveProjects();
        for (Mvp mvp : Optional.ofNullable(mvpList).orElse(Collections.emptyList())) {
            mvp.setCurrentSprint(sprintService.findSprintNumberForToday(mvp));
            this.save(mvp);
        }
    }


    public void save(Mvp mvp) {
        mvpRepository.save(mvp);
    }

    private void updateBoard(List<JiraBoard> boardList) throws JiraException {
        for (JiraBoard board : boardList) {
            try {
                if (board.getLocation() != null && board.getLocation().getProjectId() != null) {
                    Optional<Mvp> optional = mvpRepository.findByJiraProjectId(board.getLocation().getProjectId());
                    if (optional.isPresent()) {
                        Mvp mvp = optional.get();
                        // In case of several dashboards in Jira for one MVP, the smallest boardId is chosen
                        if((mvp.getJiraBoardId() == 0) || (board.getId() < mvp.getJiraBoardId())) {
                            mvp.setJiraBoardId(board.getId());
                            LOGGER.info("Mvp[" + mvp.getId() + "] -> boardId updated; " + mvp.getJiraBoardId());
                            mvpRepository.save(mvp);

                            if (mvp.getJiraBoardId() == 0) {
                                LOGGER.error("Mvp[" + mvp.getId() + "] has wrong boardId: " + mvp.getJiraBoardId());
                            }
                        }
                    }
                }
            } catch (NoSuchElementException e) {
                String message = "MVP - Unable to find MVP: " + board.getLocation().getProjectId();
                throw new JiraException(message, e);
            }
        }
    }

    private void updateUserStories(Mvp mvp, String url) throws JiraException {
        if (mvp.getJiraProjectId() != 0) {
            //Update projects
            ResponseEntity<Issues> responseEntity = getIssuesFromJira(mvp, url, Issues.class.getName());
            Integer issuesCount = (responseEntity.getBody().getTotal() != null) ? responseEntity.getBody().getTotal() : 0;
            mvp.setNbUserStories(issuesCount);
            mvpRepository.save(mvp);
        }
    }

    private void updateBugs(Mvp mvp, String url) throws JiraException {
        if (!mvp.getId().isEmpty()) {
            ResponseEntity<Issues> resultFromJira = getIssuesFromJira(mvp, url, Issues.class.getName());
            Integer bugsCount = (resultFromJira.getBody().getTotal() != null) ? resultFromJira.getBody().getTotal() : 0;
            mvp.setAllBugsCount(bugsCount);
            mvpRepository.save(mvp);
        }
    }

    private void updateResolvedBugs(Mvp mvp, String url) throws JiraException {
        int counter = 0;
        if (!mvp.getId().isEmpty()) {
            ResponseEntity<Issues> resultFromJira = getIssuesFromJira(mvp, url, Issues.class.getName());
            List<Issue> issueList = resultFromJira.getBody().getIssues();
            for (Issue issue : Optional.ofNullable(issueList).orElse(Collections.emptyList())) {
                counter = countDoneBugs(counter, issue);
            }
            mvp.setBugsCount(counter);
            mvpRepository.save(mvp);
        }
    }

    private ResponseEntity<Issues> getIssuesFromJira(Mvp mvp, String url, String className) throws JiraException {
        ResponseEntity<Issues> result = (ResponseEntity<Issues>) urlConfiguration.callJira(url, className);
        if (!result.getStatusCode().is2xxSuccessful() || result.getBody() == null) {
            String message = "MVP - Unable to find bugs of projectId : " + mvp.getId();
            throw new JiraException(message);
        }
        return result;
    }

    private int countDoneBugs(int counter, Issue issue) {
        if (issue.getFields() != null
                && issue.getFields().getStatus() != null
                && issue.getFields().getStatus().getName() != null
                && issue.getFields().getStatus().getName().equals("Done")) {
            counter++;
        }
        return counter;
    }

    private Date getResolutionDateOfRelatedUserStory(String userStoryKey) {
        Optional<UserStory> optionalUserStory = userStoryRepository.findByIssueKey(userStoryKey);
        if (optionalUserStory.isPresent()) {
            return (optionalUserStory.get().getDoneDate() != null) ? optionalUserStory.get().getDoneDate() : null;
        }
        return null;
    }

    private void updateProjectId(List<Project> projectList) {
        for (Project project : projectList) {
            try {
                Optional<Mvp> optional = mvpRepository.findById(project.getKey());
                if (optional.isPresent()) {
                    Mvp mvp = optional.get();
                    if (project.getId() != null) {
                        mvp.setJiraProjectId(Integer.parseInt(project.getId()));
                        mvpRepository.save(mvp);
                    }
                }
            } catch (NoSuchElementException e) {
                String message = "WARN : Unable to find MVP: " + project.getKey();
                LOGGER.warn(message);
            } catch (NumberFormatException e) {
                String message = "ERROR : The id returned by Jira :{} is not a int value";
                LOGGER.error(message, project.getKey());
            }
        }
    }

    public Mvp getMvpByJiraProjectId(String jiraProjectId) {

        Mvp mvp = null;
        try {
            Optional<Mvp> optional = mvpRepository.findByJiraProjectId(Integer.parseInt(jiraProjectId));

            if (optional.isPresent()) {
                mvp = optional.get();
            }
        } catch (NoSuchElementException e) {
            String message = "WARN : Unable to find MVP: " + jiraProjectId;
            LOGGER.warn(message);
        } catch (NumberFormatException e) {
            String message = "ERROR : The id returned by Jira :{} is not a int value";
            LOGGER.error(message, jiraProjectId);
        }
        return mvp;
    }
    public Mvp getMvpById(String id) {

        Mvp mvp = null;
        try {
            Optional<Mvp> optional = mvpRepository.findById(id);

            if (optional.isPresent()) {
                mvp = optional.get();
            }
        } catch (NoSuchElementException e) {
            String message = "WARN : Unable to find MVP: " + id;
            LOGGER.warn(message);
        }
        return mvp;
    }
    
}
