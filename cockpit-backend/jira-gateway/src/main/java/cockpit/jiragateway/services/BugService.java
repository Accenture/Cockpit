package cockpit.jiragateway.services;

import cockpit.cockpitcore.domaine.db.Bug;
import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.jira.Issue;
import cockpit.cockpitcore.domaine.jira.Issues;
import cockpit.jiragateway.exceptions.JiraException;
import cockpit.jiragateway.configurations.UrlConfiguration;
import cockpit.cockpitcore.repository.BugRepository;
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
public class BugService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BugService.class);

    private UrlConfiguration urlConfiguration;
    private BugRepository bugRepository;

    @Autowired
    public BugService (
            UrlConfiguration urlConfiguration, BugRepository bugRepository
    ) {
        this.urlConfiguration = urlConfiguration;
        this.bugRepository = bugRepository;
    }

    public List<Bug> updateBugsInDB(Mvp mvp, String issuesUrl) throws JiraException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String jql = "project=" +"'"+ mvp.getId() +"'"+ " AND issuetype=bug";
        String url = issuesUrl + jql;
        ResponseEntity<Issues> response = (ResponseEntity<Issues>) urlConfiguration.callJira(url, Issues.class.getName());
        if (response.getStatusCode().is2xxSuccessful()) {
            List<Issue> issues = (response.getBody().getIssues());
            for (Issue issue : Optional.ofNullable(issues).orElse(Collections.emptyList())) {
                Bug bug;
                Optional<Bug> optionalBug = bugRepository.findByIssueKey(issue.getKey());
                if (!optionalBug.isPresent()) {
                    bug = new Bug();
                } else {
                    bug = optionalBug.get();
                }
                bug.setIssueKey(issue.getKey());
                bug.setMvp(mvp);
                if (issue.getFields() != null) {
                    updateBugFields(dateFormat, issue, bug);
                }
                bugRepository.save(bug);
            }
        }
        return mvp.getBugs();
    }

    private void updateBugFields(DateFormat dateFormat, Issue issue, Bug bug) {
        if (issue.getFields() != null) {
            bug.setSummary(issue.getFields().getSummary());
            bug.setDescription("");
            //Set Priority
            bug.setPriority((issue.getFields().getPriority() == null) ? "N/A" :  issue.getFields().getPriority().getName());
            if (issue.getFields().getCustomfield10056() != null) {
                bug.setStage(issue.getFields().getCustomfield10056().toString());
            }
            try {
                bug.setCreationDate(dateFormat.parse(issue.getFields().getCreated()));
            } catch (ParseException e) {
                LOGGER.error("Exception thrown when parsing creation date", e);
            }

            //Set Environment of Detection
            if (issue.getFields().getCustomfield10072() != null) {
                Map<String,String> map = (Map<String, String>) issue.getFields().getCustomfield10072();
                bug.setEnvDetection(map.get("value"));
            }

            //Set severity
            if (issue.getFields().getCustomfield10071() != null) {
                Map<String,String> map = (Map<String, String>) issue.getFields().getCustomfield10071();
                bug.setSeverity(map.get("value"));
            }

            setRelatedUserStory(issue, bug);
            updateBugDate(dateFormat, issue, bug);
        }
    }

    private void updateBugDate(DateFormat dateFormat, Issue issue, Bug bug) {
        if (issue.getFields().getStatus() != null) {
            bug.setStatus(issue.getFields().getStatus().getName());
            if (issue.getFields().getStatus().getName() != null
                    && issue.getFields().getStatus().getName().equals("Done")) {
                if (issue.getFields().getResolutiondate() != null) {
                    try {
                        bug.setResolutionDate(dateFormat.parse(issue.getFields().getResolutiondate().toString()));
                    } catch (ParseException e) {
                        LOGGER.error("Exception thrown when parsing resolution date", e);
                    }
                } else if (issue.getFields().getAdditionalProperties() != null) {
                    setResolutionDateFromAdditionalProperties(dateFormat, issue, bug);
                }
            }

        }
    }

    private void setRelatedUserStory(Issue issue, Bug bug) {
        if (issue.getFields().getIssuelinks() != null
                && !issue.getFields().getIssuelinks().isEmpty()
                && issue.getFields().getIssuelinks().get(0).getInwardIssue() != null
                && issue.getFields().getIssuelinks().get(0).getInwardIssue().getKey() != null) {
            bug.setRelatedUserStory(issue.getFields().getIssuelinks().get(0).getInwardIssue().getKey());
        }
    }

    private void setResolutionDateFromAdditionalProperties(DateFormat dateFormat, Issue issue, Bug bug) {
        Iterator it = issue.getFields().getAdditionalProperties().entrySet().iterator();
        try {
            Map.Entry<String, String> entry = (Map.Entry) it.next();
            String resolutionDate = entry.getValue();
            if (resolutionDate != null) {
                bug.setResolutionDate(dateFormat.parse(resolutionDate));
            }
        } catch (ParseException e) {
            LOGGER.error("Unable to parse date from additional properties of the returned issue", e);
        }
    }

}
