package com.cockpit.api.service.jiragateway;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.repository.SprintRepository;
import com.cockpit.api.repository.UserStoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.*;

import static javax.management.timer.Timer.*;

@Configuration
@EnableScheduling
@Service
public class JiraGatewayService {

    private ModelMapper modelMapper = new ModelMapper();
    private final SprintRepository sprintRepository;
    private final JiraRepository jiraRepository;
    private final UserStoryRepository userStoryRepository;

    @Autowired
    public JiraGatewayService(
            JiraRepository jiraRepository,
            SprintRepository sprintRepository,
            UserStoryRepository userStoryRepository
    ) {
        this.jiraRepository = jiraRepository;
        this.sprintRepository = sprintRepository;
        this.userStoryRepository = userStoryRepository;
    }

    static Logger log = LoggerFactory.getLogger(JiraGatewayService.class);

    @Value("${spring.jira.username}")
    private String username;
    @Value("${spring.jira.token}")
    private String token;
    @Value("${spring.jira.jiraUrl}")
    private String jiraUrl;

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = ONE_MINUTE)
    public void updateProjects() throws UnirestException {
        // FIXME: Create a function for the call of JIRA API
        HttpResponse<JsonNode> response = Unirest.get(jiraUrl+"/rest/api/3/project/search")
                .basicAuth(username, token)
                .header("Accept", "application/json")
                .asJson();
        // FIXME: Create a class JiraProject to map data instead of using JSONArray (with getter setter)
        JSONArray jiraProjects = response.getBody().getObject().getJSONArray("values");
        for (Object jiraProject: jiraProjects){
            if (jiraProject instanceof JSONObject){
                Jira foundJiraProject = jiraRepository.findByJiraProjectKey(String.valueOf(((JSONObject) jiraProject).getString("key")));
                if (foundJiraProject != null){
                    foundJiraProject.setJiraProjectId(((JSONObject) jiraProject).getInt("id"));
                    jiraRepository.save(modelMapper.map(foundJiraProject, Jira.class));
                }
            }
        }
    }

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    @Transactional
    //FIXME: This function has too much cognitive complexity
    public void getJiraSprints() throws UnirestException {
        // FIXME: Create a function for the call of JIRA API
        HttpResponse<JsonNode> response = Unirest.get(jiraUrl+"/rest/agile/1.0/board?type=scrum")
                .basicAuth(username, token)
                .header("Accept", "application/json")
                .asJson();
        // FIXME: Create a class JiraBoard to map data instead of using JSONArray (with getter setter)
        JSONArray jiraBoards = response.getBody().getObject().getJSONArray("values");
        for (Object jiraBoard: jiraBoards){
            if (jiraBoard instanceof JSONObject){
                Jira foundJiraProject = jiraRepository.findByJiraProjectKey(String.valueOf(((JSONObject) jiraBoard).getJSONObject("location").getString("projectKey")));
                String boardId = String.valueOf(((JSONObject) jiraBoard).getInt("id"));
                HttpResponse<JsonNode> sprintResponse = Unirest.get(jiraUrl+"/rest/agile/1.0/board/"+boardId+"/sprint")
                        .basicAuth(username, token)
                        .header("Accept", "application/json")
                        .asJson();
                // FIXME: Create a class JiraSprint to map data instead of using JSONArray (with getter setter)
                JSONArray jiraSprints = sprintResponse.getBody().getObject().getJSONArray("values");
                int counter = 0;
                for(Object jiraSprint: jiraSprints){
                    if (jiraSprint instanceof JSONObject) {
                        counter += 1;
                        String sprintState = ((JSONObject)jiraSprint).getString("state");
                        if (foundJiraProject != null){
                            Sprint sprint = sprintRepository.findByJiraSprintId(((JSONObject)jiraSprint).getInt("id"));
                            if (sprint == null){
                                sprint = new Sprint();
                            }
                            sprint.setJiraSprintId(((JSONObject)jiraSprint).getInt("id"));
                            sprint.setSprintNumber(counter);
                            sprint.setJira(foundJiraProject);
                            sprint.setState(sprintState);
                            if(sprintState.equals("active")){
                                //FIXME: Why not use Date directly? In this case we can remove the corresponding dependency joda-time which is used only in this script
                                DateTime startDate = new DateTime(((JSONObject)jiraSprint).getString("startDate"));
                                sprint.setSprintStartDate(startDate.toDate());
                                DateTime endDate = new DateTime(((JSONObject)jiraSprint).getString("endDate"));
                                sprint.setSprintEndDate(endDate.toDate());
                            }else if(sprintState.equals("closed")){
                                DateTime startDate = new DateTime(((JSONObject)jiraSprint).getString("startDate"));
                                sprint.setSprintStartDate(startDate.toDate());
                                DateTime endDate = new DateTime(((JSONObject)jiraSprint).getString("endDate"));
                                sprint.setSprintEndDate(endDate.toDate());
                                DateTime completeDate = new DateTime(((JSONObject)jiraSprint).getString("completeDate"));
                                sprint.setSprintCompleteDate(completeDate.toDate());
                            }
                            sprintRepository.save(modelMapper.map(sprint, Sprint.class));
                            //FIXME: This log should be out of for loop if it is sprints updated
                            //log.info("Jira projects' sprints updated");
                            //FIXME: How is current sprint set for foundJiraProject before save?
                            jiraRepository.save(modelMapper.map(foundJiraProject, Jira.class));
                            //log.info("Jira projects' current sprint updated");
                        }
                    }
                }
            }
        }
    }

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    @Transactional
    //FIXME: This function has too much cognitive complexity
    public void getJiraIssues() throws UnirestException {
        // FIXME: Create a function for the call of JIRA API
        HttpResponse<JsonNode> response = Unirest.get(jiraUrl+"/rest/api/3/project/search")
                .basicAuth(username, token)
                .header("Accept", "application/json")
                .asJson();
        // FIXME: Create a class JiraProject to map data instead of using JSONArray (with getter setter)
        JSONArray jiraProjects = response.getBody().getObject().getJSONArray("values");
        for (Object jiraProject: jiraProjects){
            if (jiraProject instanceof JSONObject){
                String projectKey = (String.valueOf(((JSONObject) jiraProject).getString("key")));
                Jira foundJiraProject = jiraRepository.findByJiraProjectKey(projectKey);
                if (foundJiraProject != null){
                    int pagination = 0;
                    JsonNodeFactory jnf = JsonNodeFactory.instance;
                    ObjectNode payload = jnf.objectNode();
                    ArrayNode expand = payload.putArray("expand");
                    expand.add("names");
                    expand.add("schema");
                    expand.add("versionedRepresentations");
                    payload.put("jql", "project = "+projectKey);
                    ArrayNode fields = payload.putArray("fields");
                    fields.add("summary");
                    fields.add("status");
                    fields.add("issuetype");
                    fields.add("project");
                    fields.add("priority");
                    fields.add("created");
                    fields.add("description");
                    fields.add("created");
                    fields.add("resolutiondate");
                    fields.add("customfield_10026");
                    fields.add("customfield_10020");
                    payload.put("maxResults", 100);
                    payload.put("startAt", pagination);

                    Unirest.setObjectMapper(new ObjectMapper() {
                        private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                                = new com.fasterxml.jackson.databind.ObjectMapper();

                        public <T> T readValue(String value, Class<T> valueType) {
                            try {
                                return jacksonObjectMapper.readValue(value, valueType);
                            } catch (IOException e) {
                                log.error(String.valueOf(e));
                            }
                            return null;
                        }

                        public String writeValue(Object value) {
                            try {
                                return jacksonObjectMapper.writeValueAsString(value);
                            } catch (JsonProcessingException e) {
                                log.error(String.valueOf(e));
                            }
                            return null;
                        }
                    });

                    HttpResponse<JsonNode> jiraProjectIssues = Unirest.post(jiraUrl+"/rest/api/3/search")
                            .basicAuth(username, token)
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .body(payload)
                            .asJson();
                    // FIXME: Create a class JiraIssue to map data instead of using JSONArray (with getter setter)
                    while(jiraProjectIssues.getBody().getObject().getInt("total") >= pagination){
                        JSONArray jiraIssues = jiraProjectIssues.getBody().getObject().getJSONArray("issues");
                        if (jiraIssues.length() > 0){
                            for (Object jiraIssue: jiraIssues) {
                                if (jiraIssue instanceof JSONObject && ((JSONObject) jiraIssue).getJSONObject("versionedRepresentations").getJSONObject("issuetype").getJSONObject("1").getString("name").equals("Story")) {
                                    UserStory issue = userStoryRepository.findByIssueKey(String.valueOf(((JSONObject) jiraIssue).getString("key")));
                                    if (issue == null){
                                        issue = new UserStory();
                                    }
                                    DateTime creationDate = new DateTime(((JSONObject) jiraIssue).getJSONObject("versionedRepresentations").getJSONObject("created").getString("1"));
                                    issue.setCreationDate(creationDate.toDate());
                                    if (((JSONObject) jiraIssue).getJSONObject("versionedRepresentations").getJSONObject("status").getJSONObject("1").getString("name").equals("Done")){
                                        DateTime doneDate = new DateTime(((JSONObject) jiraIssue).getJSONObject("versionedRepresentations").getJSONObject("created").getString("1"));
                                        issue.setDoneDate(doneDate.toDate());
                                    }
                                    // TODO: Replace it by == null
                                    if (!String.valueOf(((JSONObject) jiraIssue).getJSONObject("versionedRepresentations").getJSONObject("customfield_10026").get("1")).equals("null")){
                                        issue.setStoryPoint((Double) ((JSONObject) jiraIssue).getJSONObject("versionedRepresentations").getJSONObject("customfield_10026").get("1"));
                                    }
                                    issue.setIssueKey(((JSONObject) jiraIssue).getString("key"));
                                    issue.setJiraIssueId(((JSONObject) jiraIssue).getInt("id"));
                                    issue.setSummary(((JSONObject) jiraIssue).getJSONObject("versionedRepresentations").getJSONObject("summary").getString("1"));
                                    issue.setPriority(((JSONObject) jiraIssue).getJSONObject("versionedRepresentations").getJSONObject("priority").getJSONObject("1").getString("name"));
                                    issue.setStatus(((JSONObject) jiraIssue).getJSONObject("versionedRepresentations").getJSONObject("status").getJSONObject("1").getString("name"));
                                    JSONArray listSprints = ((JSONObject) jiraIssue).getJSONObject("versionedRepresentations").getJSONObject("customfield_10020").getJSONArray("2");
                                    if (listSprints.length() != 0){
                                        List<JSONObject> sortedSprints = new ArrayList<>();
                                        for (int i=0; i < listSprints.length(); i++){
                                            sortedSprints.add(listSprints.getJSONObject(i));
                                        }
                                        Collections.sort(sortedSprints, (jsonObjectA, jsonObjectB) -> {
                                            int compare = 0;
                                            try{
                                                int idA = jsonObjectA.getInt("id");
                                                int idB = jsonObjectB.getInt("id");
                                                compare = Integer.compare(idA, idB);
                                            }catch (JSONException e){
                                                log.error(String.valueOf(e));
                                            }
                                            return compare;
                                        });
                                        JSONObject currentSprint = sortedSprints.get(0);
                                        Sprint issueSprint = sprintRepository.findByJiraSprintId(currentSprint.getInt("id"));
                                        if (issueSprint != null ) {
                                            issue.setSprint(issueSprint);
                                        }
                                    }
                                    issue.setJira(foundJiraProject);
                                    userStoryRepository.save(modelMapper.map(issue, UserStory.class));
                                }
                            }
                            pagination += 100;
                            payload.put("startAt", pagination);
                            jiraProjectIssues = Unirest.post(jiraUrl+"/rest/api/3/search")
                                    .basicAuth(username, token)
                                    .header("Accept", "application/json")
                                    .header("Content-Type", "application/json")
                                    .body(payload)
                                    .asJson();
                        }else{
                            break;
                        }
                    }
                }
            }
        }
    }

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    public void setTotalNbOfUserStoryForEachSprintOfEachProject() {
        List<Jira> jiraProjectList = jiraRepository.findAllByOrderById();
        for (Jira jira : jiraProjectList) {
            List<Sprint> sprintList = sprintRepository.findByJiraOrderBySprintNumber(jira);
            int totalNumberOfUserStoriesUntilCurrentSprint = userStoryRepository.countUserStoriesByJiraAndCreationDateBefore(jira, sprintList.get(0).getSprintStartDate());
                for(Sprint sprint: sprintList) {
                Date sprintStartDate = sprint.getSprintStartDate();
                Date sprintEndDate = sprint.getSprintEndDate();
                if (sprintStartDate != null && sprintEndDate != null) {
                    int nbUserStoriesCreatedDuringCurrentSprint= userStoryRepository.countUserStoriesByJiraAndCreationDateGreaterThanAndCreationDateLessThanEqual(jira, sprintStartDate, sprintEndDate);
                    totalNumberOfUserStoriesUntilCurrentSprint += nbUserStoriesCreatedDuringCurrentSprint;
                    sprint.setTotalNbUs(totalNumberOfUserStoriesUntilCurrentSprint);
                    sprintRepository.save(sprint);
                }
            }
        }
    }
}
