package com.cockpit.api.service.jiragateway;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.serializer.*;
import com.cockpit.api.model.dto.JiraSprintDTO;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.repository.SprintRepository;
import com.cockpit.api.repository.UserStoryRepository;
import com.cockpit.api.service.UserStoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
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

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.*;
import static javax.management.timer.Timer.ONE_SECOND;
import static javax.management.timer.Timer.ONE_MINUTE;

@Configuration
@EnableScheduling
@Service
public class JiraGatewayService {

    private final ModelMapper modelMapper = new ModelMapper();
    private final SprintRepository sprintRepository;
    private final JiraRepository jiraRepository;
    private final UserStoryRepository userStoryRepository;
    final UserStoryService userStoryService;
    private static final String VALUESFIELD ="values";
    private static final String STATUSFIELD ="status";
    private static final String CREATEDFIELD = "created";
    private static final String HEADERKEY = "Accept";
    private static final String HEADERVALUE = "application/json";


    @Autowired
    public JiraGatewayService(
            JiraRepository jiraRepository,
            SprintRepository sprintRepository,
            UserStoryRepository userStoryRepository,
            UserStoryService userStoryService
    ) {
        this.jiraRepository     = jiraRepository;
        this.sprintRepository   = sprintRepository;
        this.userStoryRepository = userStoryRepository;
        this.userStoryService = userStoryService;
    }

    static Logger log = LoggerFactory.getLogger(JiraGatewayService.class);

    @Value("${spring.jira.username}")
    private String username;
    @Value("${spring.jira.token}")
    private String token;
//    @Value("${spring.jira.jiraUrl}")
    private static final String jiraUrl="https://tdf.atlassian.net";
//    private String token="TmgJIsfKVlvTMIL1neS2DE5B";
//    private String username="khojiakbar.abdullazoda@external.total.com";

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    public void updateProjects() throws UnirestException, JsonProcessingException {
        HttpResponse<JsonNode> jiraProjects = getJira();
        JSONArray jiraProjectsList = jiraProjects.getBody().getObject().getJSONArray(VALUESFIELD);
        for (Object jiraProject: jiraProjectsList){
            if (jiraProject instanceof JSONObject){
                ObjectMapper mapper = new ObjectMapper();
                JiraProjectDTO jProject = mapper.readValue(jiraProject.toString(), JiraProjectDTO.class);
                Jira foundJiraProject = jiraRepository.findByJiraProjectKey(jProject.getKey());
                log.warn(String.valueOf(foundJiraProject));
                if (foundJiraProject != null){
                    foundJiraProject.setJiraProjectId(jProject.getId());
                    jiraRepository.save(modelMapper.map(foundJiraProject, Jira.class));
                }
            }
        }
    }

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    @Transactional
    public void getJiraSprints() throws UnirestException, JsonProcessingException {
        HttpResponse<JsonNode> response = Unirest.get(jiraUrl+"/rest/agile/1.0/board?type=scrum")
                .basicAuth(username, token)
                .header(HEADERKEY, HEADERVALUE)
                .asJson();
        JSONArray jiraBoards = response.getBody().getObject().getJSONArray(VALUESFIELD);
        for (Object jiraBoard: jiraBoards){
            if (jiraBoard instanceof JSONObject){
                ObjectMapper mapper = new ObjectMapper();
                JiraBoardDTO jbProject = mapper.readValue(jiraBoard.toString(), JiraBoardDTO.class);
                Jira foundJiraProject = jiraRepository.findByJiraProjectKey(jbProject.getLocation().getProjectKey());
                HttpResponse<JsonNode> sprintResponse = Unirest.get(jiraUrl+"/rest/agile/1.0/board/"+jbProject.getId()+"/sprint")
                        .basicAuth(username, token)
                        .header(HEADERKEY, HEADERVALUE)
                        .asJson();
                JSONArray jiraSprints = sprintResponse.getBody().getObject().getJSONArray(VALUESFIELD);
                saveSprints(jiraSprints, foundJiraProject);
            }
        }
    }

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void getJiraIssues() throws UnirestException, JsonProcessingException {
        String storyPointsField = getFieldId("Story Points");
        String sprintField = getFieldId("Sprint");
        HttpResponse<JsonNode> response = getJira();
        JSONArray jiraProjects = response.getBody().getObject().getJSONArray(VALUESFIELD);
        for (Object jiraProject: jiraProjects){
            if (jiraProject instanceof JSONObject){
                ObjectMapper mapper = new ObjectMapper();
                JiraProjectDTO jProject = mapper.readValue(jiraProject.toString(), JiraProjectDTO.class);
                Jira foundJiraProject = jiraRepository.findByJiraProjectKey(jProject.getKey());
                if (foundJiraProject != null){
                    int pagination = 0;
                    ObjectNode payload = getIssuesBody(jProject, pagination, storyPointsField, sprintField);
                    mapBodyObject();
                    HttpResponse<JsonNode> jiraProjectIssues = searchInJira(payload);
                    while(jiraProjectIssues.getBody().getObject().getInt("total") >= pagination){
                        JSONArray jiraIssues = jiraProjectIssues.getBody().getObject().getJSONArray("issues");
                        if (jiraIssues.length() > 0){
                            saveIssues(jiraIssues, foundJiraProject, storyPointsField, sprintField);
                            pagination += 100;
                            payload.put("startAt", pagination);
                            jiraProjectIssues = searchInJira(payload);
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

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    public void deleteJiraProjects() throws UnirestException, JsonProcessingException {
        List<Jira> jiraProjectList = jiraRepository.findAllByOrderById();
        JSONArray jiraProjects = getJiraProjects();
        for (Jira foundJiraInDB: jiraProjectList){
            boolean found = false;
            for (Object jiraProject: jiraProjects){
                ObjectMapper mapper = new ObjectMapper();
                JiraProjectDTO jProject = mapper.readValue(jiraProject.toString(), JiraProjectDTO.class);
                if (foundJiraInDB.getJiraProjectKey().equals(jProject.getKey())){
                    found = true;
                }
            }
            if (!found){
                jiraRepository.delete(foundJiraInDB);
            }

        }
    }

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    public void deleteJiraSprint() throws UnirestException, JsonProcessingException {
        List<Sprint> sprintList = sprintRepository.findAll();
        HttpResponse<JsonNode> boards = Unirest.get(jiraUrl+"/rest/agile/1.0/board?type=scrum")
                .basicAuth(username, token)
                .header(HEADERKEY, HEADERVALUE)
                .asJson();
        JSONArray boardList = boards.getBody().getObject().getJSONArray(VALUESFIELD);
        for(Sprint foundSprintDb : sprintList){
            boolean found = false;
            for (Object board : boardList){
                ObjectMapper mapper = new ObjectMapper();
                BoardsDTO boardsDTO = mapper.readValue(board.toString(), BoardsDTO.class);
                HttpResponse<JsonNode> sprints = Unirest.get(jiraUrl+"/rest/agile/1.0/board/"+boardsDTO.getId()+"/sprint")
                        .basicAuth(username, token)
                        .header(HEADERKEY, HEADERVALUE)
                        .asJson();
                JSONArray boardSprints = sprints.getBody().getObject().getJSONArray(VALUESFIELD);
                for (Object sprint : boardSprints){
                    BoardSprintDTO boardSprintDTO = mapper.readValue(sprint.toString(), BoardSprintDTO.class);
                    if (foundSprintDb.getId().equals(boardSprintDTO.getId())){
                        found = true;
                    }
                }
            }
            if (!found){
                sprintRepository.delete(foundSprintDb);
            }
        }
    }

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    public void deleteJiraIssues() throws UnirestException, JsonProcessingException {
        List<UserStory> userStories = userStoryRepository.findAll();
        HttpResponse<JsonNode> jiraIssues = Unirest.get(jiraUrl+"/rest/api/3/search?jql=issuetype=Story")
                .basicAuth(username, token)
                .header(HEADERKEY, HEADERVALUE)
                .asJson();
        JSONArray jiraIssueList = jiraIssues.getBody().getObject().getJSONArray("issues");

        for (UserStory userStory : userStories){
            boolean found = false;
            for (Object jiraIssue : jiraIssueList){
                ObjectMapper mapper = new ObjectMapper();
                IssueDTO issueDTO = mapper.readValue(jiraIssue.toString(), IssueDTO.class);
                if (userStory.getIssueKey().equals(issueDTO.getKey())){
                    found = true;
                }
            }
            if (!found){
                userStoryRepository.delete(userStory);
            }
        }
    }


    public HttpResponse<JsonNode> getJira() throws UnirestException {
        return Unirest.get(jiraUrl+"/rest/api/3/project/search")
                .basicAuth(username, token)
                .header(HEADERKEY, HEADERVALUE)
                .asJson();
    }

    public void saveSprints(JSONArray jiraSprints, Jira foundJiraProject) throws JsonProcessingException {
        int counter = 0;
        ObjectMapper mapper = new ObjectMapper();
        for(Object jiraSprint: jiraSprints){
            if (jiraSprint instanceof JSONObject) {
                counter += 1;
                JiraSprintDTO jsProject = mapper.readValue(jiraSprint.toString(), JiraSprintDTO.class);
                String sprintState = jsProject.getState();
                if (foundJiraProject != null){
                    Sprint sprint = sprintRepository.findByJiraSprintId(jsProject.getId());
                    if (sprint == null){
                        sprint = new Sprint();
                    }
                    sprint.setJiraSprintId(jsProject.getId());
                    sprint.setSprintNumber(counter);
                    sprint.setJira(foundJiraProject);
                    sprint.setState(sprintState);
                    if(sprintState.equals("active")){
                        sprint.setSprintStartDate(jsProject.getStartDate());
                        sprint.setSprintEndDate(jsProject.getEndDate());
                        foundJiraProject.setCurrentSprint(counter);
                    }else if(sprintState.equals("closed")){
                        sprint.setSprintStartDate(jsProject.getStartDate());
                        sprint.setSprintEndDate(jsProject.getEndDate());
                        sprint.setSprintCompleteDate(jsProject.getCompleteDate());
                    }
                    sprintRepository.save(modelMapper.map(sprint, Sprint.class));
                    jiraRepository.save(modelMapper.map(foundJiraProject, Jira.class));
                }
            }
        }
    }

    public ObjectNode getIssuesBody(JiraProjectDTO jProject, int pagination, String storyPointsField, String sprintField){
        JsonNodeFactory jnf = JsonNodeFactory.instance;
        ObjectNode payload = jnf.objectNode();
        ArrayNode expand = payload.putArray("expand");
        expand.add("names");
        expand.add("schema");
        expand.add("versionedRepresentations");
        payload.put("jql", "project = "+jProject.getKey());
        ArrayNode fields = payload.putArray("fields");
        fields.add("summary");
        fields.add(STATUSFIELD);
        fields.add("issuetype");
        fields.add("project");
        fields.add("priority");
        fields.add(CREATEDFIELD);
        fields.add("description");
        fields.add("resolutiondate");
        fields.add(storyPointsField);
        fields.add(sprintField);
        payload.put("maxResults", 100);
        payload.put("startAt", pagination);
        return payload;
    }

    public void mapBodyObject(){
        Unirest.setObjectMapper(new com.mashape.unirest.http.ObjectMapper() {
            private final com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
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
    }

    public void sortAndSaveBySprintId(JSONArray listSprints, UserStory issue){
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

    public void saveIssues(JSONArray jiraIssues, Jira foundJiraProject, String storyPointsField, String sprintField){
        for (Object jiraIssue: jiraIssues) {
            JSONObject issueVersionedRep = ((JSONObject) jiraIssue).getJSONObject("versionedRepresentations");
            if (issueVersionedRep.getJSONObject("issuetype").getJSONObject("1").getString("name").equals("Story")) {
                UserStory issue = userStoryRepository.findByIssueKey(String.valueOf(((JSONObject) jiraIssue).getString("key")));
                if (issue == null){
                    issue = new UserStory();
                }
                DateTime creationDate = new DateTime(issueVersionedRep.getJSONObject(CREATEDFIELD).getString("1"));
                issue.setCreationDate(creationDate.toDate());
                if (issueVersionedRep.getJSONObject(STATUSFIELD).getJSONObject("1").getString("name").equals("Done")){
                    DateTime doneDate = new DateTime(issueVersionedRep.getJSONObject(CREATEDFIELD).getString("1"));
                    issue.setDoneDate(doneDate.toDate());
                }
                Object sprintDetails = (issueVersionedRep.getJSONObject(storyPointsField).get("1"));
                if (sprintDetails == null){
                    issue.setStoryPoint((Double) issueVersionedRep.getJSONObject(storyPointsField).get("1"));
                }
                issue.setIssueKey(((JSONObject) jiraIssue).getString("key"));
                issue.setJiraIssueId(((JSONObject) jiraIssue).getInt("id"));
                issue.setSummary(issueVersionedRep.getJSONObject("summary").getString("1"));
                issue.setPriority(issueVersionedRep.getJSONObject("priority").getJSONObject("1").getString("name"));
                issue.setStatus(issueVersionedRep.getJSONObject(STATUSFIELD).getJSONObject("1").getString("name"));
                JSONArray listSprints = issueVersionedRep.getJSONObject(sprintField).getJSONArray("2");
                if (listSprints.length() != 0){
                    sortAndSaveBySprintId(listSprints, issue);
                }
                issue.setJira(foundJiraProject);
                userStoryRepository.save(modelMapper.map(issue, UserStory.class));
            }
        }
    }

    public HttpResponse<JsonNode> searchInJira(ObjectNode payload) throws UnirestException {
        return Unirest.post(jiraUrl+"/rest/api/3/search")
                .basicAuth(username, token)
                .header(HEADERKEY, HEADERVALUE)
                .header("Content-Type", HEADERVALUE)
                .body(payload)
                .asJson();
    }

    public JSONArray getJiraProjects() throws UnirestException {
        HttpResponse<JsonNode> jiraProjectsResponse = Unirest.get(jiraUrl+"/rest/api/3/project/")
                .basicAuth(username, token)
                .header(HEADERKEY, HEADERVALUE)
                .asJson();
        return jiraProjectsResponse.getBody().getArray();
    }

    public String getFieldId(String fieldName) throws UnirestException, JsonProcessingException {
        HttpResponse<JsonNode> fieldList = Unirest.get(jiraUrl+"/rest/api/3/field?name=Sprint/")
                .basicAuth(username, token)
                .header(HEADERKEY, HEADERVALUE)
                .asJson();
        JSONArray fields = fieldList.getBody().getArray();
        for(Object field : fields){
            ObjectMapper mapper = new ObjectMapper();
            FieldsDTO fieldsDTO = mapper.readValue(field.toString(), FieldsDTO.class);
            if (fieldsDTO.getName().equals(fieldName)){
                return fieldsDTO.getId();
            }
        }
        return null;
    }
}
