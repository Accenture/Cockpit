package com.cockpit.api.service.jiragateway;

import static javax.management.timer.Timer.ONE_HOUR;
import static javax.management.timer.Timer.ONE_MINUTE;
import static javax.management.timer.Timer.ONE_SECOND;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.jira.Board;
import com.cockpit.api.model.dto.jira.JiraBoard;
import com.cockpit.api.model.dto.jira.Project;
import com.cockpit.api.model.dto.jira.SprintHeaders;
import com.cockpit.api.model.dto.jira.SprintJira;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.repository.SprintRepository;
import com.cockpit.api.repository.UserStoryRepository;
import com.cockpit.api.service.UserStoryService;

@Configuration
@EnableScheduling
@Service
public class JiraGatewayService {
    Logger log = LoggerFactory.getLogger(JiraGatewayService.class);

    private List<Sprint> sprintsToRemove = new ArrayList<>();
    HttpEntity<String> request;
    HttpHeaders headers;

    RestTemplate restTemplate = new RestTemplate();

    private final ModelMapper modelMapper = new ModelMapper();

    private final SprintRepository sprintRepository;
    private final JiraRepository jiraRepository;
    private final UserStoryRepository userStoryRepository;
    final UserStoryService userStoryService;
    private static final String VALUESFIELD = "values";
    private static final String STATUSFIELD = "status";
    private static final String CREATEDFIELD = "created";
    private static final String HEADERKEY = "Accept";
    private static final String HEADERVALUE = "application/json";

    @Autowired
    public JiraGatewayService(JiraRepository jiraRepository, SprintRepository sprintRepository,
                              UserStoryRepository userStoryRepository, UserStoryService userStoryService) {
        this.jiraRepository = jiraRepository;
        this.sprintRepository = sprintRepository;
        this.userStoryRepository = userStoryRepository;
        this.userStoryService = userStoryService;
    }
    
    @Value("${spring.jira.username}")
    private String username;
    @Value("${spring.jira.token}")
    private String token;
    @Value("${spring.jira.jiraUrl}")
    private String jiraUrl;
    @Value("${spring.jira.urlProjects}")
    private String urlProjects;
    @Value("${spring.jira.urlBoards}")
    private String urlBoards;
    @Value("${spring.jira.urlSprints}")
    private String urlSprints;


    // added by Rihab
    public HttpHeaders addAuthorizationToHeaders() {

        String jiraCredentials = username + ":" + token;
        byte[] plainCredsBytes = jiraCredentials.getBytes();
        byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        return headers;

    }

    public ResponseEntity<?> callJira(String url, String className) throws Exception {

        headers = this.addAuthorizationToHeaders();
        request = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, Class.forName(className));
        } catch (Exception e) {
            throw new Exception(" Exception jira update projects", e);
        }
        return responseEntity;
    }

    // added by Rihab

    @Scheduled(initialDelay = 5 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    public void updateProjectId() throws Exception {
        ResponseEntity<Project[]> response = (ResponseEntity<Project[]>) callJira(urlProjects,
                Project[].class.getName());
        List<Project> jiraProjectsList = Arrays.asList(response.getBody());
        List<Jira> jiraList = jiraRepository.findAllByOrderById();
        for (Jira jira : jiraList) {
            if (jiraProjectsList.stream().filter(projet -> projet.getKey().equals(jira.getJiraProjectKey())).findFirst()
                    .isPresent()) {
                Project jProjet = jiraProjectsList.stream()
                        .filter(projet -> projet.getKey().equals(jira.getJiraProjectKey())).findFirst().get();
                jira.setJiraProjectId(Integer.parseInt(jProjet.getId()));
                jiraRepository.save(jira);
            }

        }

    }

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 2 * ONE_HOUR)
    public void updateBoardIdInJira() {
        try {
            updateBoardIdInJira(urlBoards);
        } catch (Exception e) {
        }
    }

    @Scheduled(initialDelay = 3 * ONE_SECOND, fixedDelay = ONE_HOUR)
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
            List<Sprint> sprintsToRemove = getSprintsToRemove();
            cleanWrongSprintFromMvp(sprintsToRemove);
        } catch (Exception e) {
        }
    }

    /* @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    @Transactional
    public void getJiraSprints() throws UnirestException, JsonProcessingException {

        HttpResponse<JsonNode> response = Unirest.get(jiraUrl + "/rest/agile/1.0/board?type=scrum")
                .basicAuth(username, token).header(HEADERKEY, HEADERVALUE).asJson();
        JSONArray jiraBoards = response.getBody().getObject().getJSONArray(VALUESFIELD);
        for (Object jiraBoard : jiraBoards) {
            if (jiraBoard instanceof JSONObject) {
                ObjectMapper mapper = new ObjectMapper();
                JiraBoardDTO jbProject = mapper.readValue(jiraBoard.toString(), JiraBoardDTO.class);
                Jira foundJiraProject = jiraRepository.findByJiraProjectKey(jbProject.getLocation().getProjectKey());
                HttpResponse<JsonNode> sprintResponse = Unirest
                        .get(jiraUrl + "/rest/agile/1.0/board/" + jbProject.getId() + "/sprint")
                        .basicAuth(username, token).header(HEADERKEY, HEADERVALUE).asJson();
                JSONArray jiraSprints = sprintResponse.getBody().getObject().getJSONArray(VALUESFIELD);
                saveSprints(jiraSprints, foundJiraProject);
            }
        }
    }
*/
    public void cleanWrongSprintFromMvp(List<Sprint> sprints) {
        if (sprints != null && !sprints.isEmpty()) {
            for (Sprint sprint : sprints) {

                sprintRepository.delete(sprint);
            }
            sprints.clear();
        }
    }

    public List<Sprint> getSprintsToRemove() {
        return sprintsToRemove;
    }

    public List<SprintJira> getSprintsFromJira(int boardId, String urlSprints) throws Exception {
        ResponseEntity<SprintHeaders> result = (ResponseEntity<SprintHeaders>) callJira(
                urlSprints + boardId + "/sprint", SprintHeaders.class.getName());
        List<SprintJira> newSprintsList = null;
        if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            newSprintsList = (result.getBody().getValues());
        }
        return newSprintsList;
    }

    public void reorderSprintNumberPerJira(Jira jira) throws Exception {
        List<Sprint> mvpSprints;
        mvpSprints = sprintRepository.findByJiraOrderBySprintNumber(jira);

        if (mvpSprints != null || !mvpSprints.isEmpty()) {
            int count = 0;
            for (Sprint sprint : mvpSprints) {
                sprint.setSprintNumber(count);
                sprintRepository.save(sprint);
                count++;
            }
        }
    }

    public void updateSprintsInDB(List<SprintJira> sprintJiraList, Jira jira) {
        int sprintNumber = 1;
        for (SprintJira sprintJira : Optional.ofNullable(sprintJiraList).orElse(Collections.emptyList())) {
            Sprint sprintExist = sprintRepository.findByJiraSprintId(sprintJira.getId());
            if (sprintExist == null && sprintJira.getOriginBoardId() == jira.getBoardId()) {
                Sprint newSprint = new Sprint();
                newSprint.setJiraSprintId(sprintJira.getId());
                newSprint.setState(sprintJira.getState());
                newSprint.setJira(jira);
                newSprint.setSprintNumber(sprintNumber);

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
    }

    public void updateBoardIdInJira(String urlBoards) throws Exception {
        List<JiraBoard> foundJiraBoards = new ArrayList<>();
        List<JiraBoard> boardList = null;

        int maxResults = 50;
        int startAt = 0;
        int numberOfBoardIdReceived = 0;
        int totalBoardId;

        do {
            String url = String.format(urlBoards, maxResults, startAt);
            ResponseEntity<Board> result = (ResponseEntity<Board>) callJira(url, Board.class.getName());

            if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
                maxResults = result.getBody().getMaxResults();
                startAt = result.getBody().getStartAt();
                totalBoardId = result.getBody().getTotal();
                numberOfBoardIdReceived = startAt + maxResults;

                boardList = result.getBody().getValues();
                updateBoard(boardList);
            } else {
                throw new Exception("jira - Response code : while trying to update board for the jira ");
            }
            startAt = startAt + maxResults;
            foundJiraBoards.addAll(boardList);
        } while (numberOfBoardIdReceived < totalBoardId);
    }

    private void updateBoard(List<JiraBoard> boardList) throws Exception {
        for (JiraBoard board : boardList) {
            try {
                if (board.getLocation() != null && board.getLocation().getProjectId() != null) {
                    Optional<Jira> optional = jiraRepository.findByJiraProjectId((board.getLocation().getProjectId()));
                    if (optional.isPresent()) {
                        Jira jira = optional.get();
                        jira.setBoardId(board.getId());
                        jiraRepository.save(jira);
                    }
                }
            } catch (Exception e) {
                String message = "Jira - Unable to find Jira: " + board.getLocation().getProjectId();
                throw new Exception(message, e);
            }
        }
    }

    /*
     *
     * @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
     *
     * @Scheduled(fixedDelay = 1000)
     *
     * @Transactional public void getJiraIssues() throws UnirestException,
     * JsonProcessingException { String storyPointsField =
     * getFieldId("Story Points"); String sprintField = getFieldId("Sprint");
     * JSONArray jiraProjects = getJira(); for (Object jiraProject :
     * jiraProjects) { if (jiraProject instanceof JSONObject) { ObjectMapper
     * mapper = new ObjectMapper(); JiraProjectDTO jProject =
     * mapper.readValue(jiraProject.toString(), JiraProjectDTO.class); Jira
     * foundJiraProject =
     * jiraRepository.findByJiraProjectKey(jProject.getKey()); if
     * (foundJiraProject != null) { int pagination = 0; ObjectNode payload =
     * getIssuesBody(jProject, pagination, storyPointsField, sprintField);
     * mapBodyObject(); HttpResponse<JsonNode> jiraProjectIssues =
     * searchInJira(payload); while
     * (jiraProjectIssues.getBody().getObject().getInt("total") >= pagination) {
     * JSONArray jiraIssues =
     * jiraProjectIssues.getBody().getObject().getJSONArray("issues"); if
     * (jiraIssues.length() > 0) { saveIssues(jiraIssues, foundJiraProject,
     * storyPointsField, sprintField); pagination += 100; payload.put("startAt",
     * pagination); jiraProjectIssues = searchInJira(payload); } else { break; }
     * } } } } }
     *
     * @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
     * public void setTotalNbOfUserStoryForEachSprintOfEachProject() {
     * List<Jira> jiraProjectList = jiraRepository.findAllByOrderById(); for
     * (Jira jira : jiraProjectList) { List<Sprint> sprintList =
     * sprintRepository.findByJiraOrderBySprintNumber(jira); int
     * totalNumberOfUserStoriesUntilCurrentSprint = 0; if (sprintList.size() >
     * 0) { totalNumberOfUserStoriesUntilCurrentSprint = userStoryRepository
     * .countUserStoriesByJiraAndCreationDateBefore(jira,
     * sprintList.get(0).getSprintStartDate()); } for (Sprint sprint :
     * sprintList) { Date sprintStartDate = sprint.getSprintStartDate(); Date
     * sprintEndDate = sprint.getSprintEndDate(); if (sprintStartDate != null &&
     * sprintEndDate != null) { int nbUserStoriesCreatedDuringCurrentSprint =
     * userStoryRepository
     * .countUserStoriesByJiraAndCreationDateGreaterThanAndCreationDateLessThanEqual
     * (jira, sprintStartDate, sprintEndDate);
     * totalNumberOfUserStoriesUntilCurrentSprint +=
     * nbUserStoriesCreatedDuringCurrentSprint;
     * sprint.setTotalNbUs(totalNumberOfUserStoriesUntilCurrentSprint);
     * sprintRepository.save(sprint); } } } }
     *
     * @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
     * public void deleteJiraProjects() throws UnirestException,
     * JsonProcessingException { List<Jira> jiraProjectList =
     * jiraRepository.findAllByOrderById(); JSONArray jiraProjects =
     * getJiraProjects(); for (Jira foundJiraInDB : jiraProjectList) { boolean
     * found = false; for (Object jiraProject : jiraProjects) { ObjectMapper
     * mapper = new ObjectMapper(); JiraProjectDTO jProject =
     * mapper.readValue(jiraProject.toString(), JiraProjectDTO.class); if
     * (foundJiraInDB.getJiraProjectKey().equals(jProject.getKey())) { found =
     * true; } } if (!found) { jiraRepository.delete(foundJiraInDB); } } }
     *
     * @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
     * public void deleteJiraSprint() throws UnirestException,
     * JsonProcessingException { List<Sprint> sprintList =
     * sprintRepository.findAll(); JSONArray boardList = getSprintList(); for
     * (Sprint foundSprintDb : sprintList) { boolean found = false; for (Object
     * board : boardList) { ObjectMapper mapper = new ObjectMapper(); BoardsDTO
     * boardsDTO = mapper.readValue(board.toString(), BoardsDTO.class);
     * JSONArray boardSprints = getJiraSprints(boardsDTO.getId()); for (Object
     * sprint : boardSprints) { BoardSprintDTO boardSprintDTO =
     * mapper.readValue(sprint.toString(), BoardSprintDTO.class); if
     * (foundSprintDb.getId().equals(boardSprintDTO.getId())) { found = true; }
     * } } if (!found) { sprintRepository.delete(foundSprintDb); } } }
     *
     * @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
     * public void deleteJiraIssues() throws UnirestException,
     * JsonProcessingException { List<UserStory> userStories =
     * userStoryRepository.findAll(); JSONArray jiraIssueList =
     * getJiraIssueList(); for (UserStory userStory : userStories) { boolean
     * found = false; for (Object jiraIssue : jiraIssueList) { ObjectMapper
     * mapper = new ObjectMapper(); IssueDTO issueDTO =
     * mapper.readValue(jiraIssue.toString(), IssueDTO.class); if
     * (userStory.getIssueKey().equals(issueDTO.getKey())) { found = true; } }
     * if (!found) { userStoryRepository.delete(userStory); } } }
     *
     * public HttpResponse<JiraProjectDTO[]> getJira() throws UnirestException {
     * HttpResponse<JiraProjectDTO[]> jiraProjectsResponse = Unirest.get(jiraUrl
     * + "/rest/api/3/project/search") .basicAuth(username,
     * token).header(HEADERKEY, HEADERVALUE).asObject(JiraProjectDTO[].class);
     * return jiraProjectsResponse; }
     */

/*	public void saveSprints(JSONArray jiraSprints, Jira foundJiraProject) throws JsonProcessingException {
		int counter = 0;
		ObjectMapper mapper = new ObjectMapper();
		for (Object jiraSprint : jiraSprints) {
			if (jiraSprint instanceof JSONObject) {
				counter += 1;
				JiraSprintDTO jsProject = mapper.readValue(jiraSprint.toString(), JiraSprintDTO.class);
				String sprintState = jsProject.getState();
				if (foundJiraProject != null) {
					Sprint sprint = sprintRepository.findByJiraSprintId(jsProject.getId());
					if (sprint == null) {
						sprint = new Sprint();
					}
					sprint.setJiraSprintId(jsProject.getId());
					sprint.setSprintNumber(counter);
					sprint.setJira(foundJiraProject);
					sprint.setState(sprintState);
					if (sprintState.equals("active")) {
						sprint.setSprintStartDate(jsProject.getStartDate());
						sprint.setSprintEndDate(jsProject.getEndDate());
						foundJiraProject.setCurrentSprint(counter);
					} else if (sprintState.equals("closed")) {
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
	/*
	 * public ObjectNode getIssuesBody(JiraProjectDTO jProject, int pagination,
	 * String storyPointsField, String sprintField) { JsonNodeFactory jnf =
	 * JsonNodeFactory.instance; ObjectNode payload = jnf.objectNode();
	 * ArrayNode expand = payload.putArray("expand"); expand.add("names");
	 * expand.add("schema"); expand.add("versionedRepresentations");
	 * payload.put("jql", "project = " + jProject.getKey()); ArrayNode fields =
	 * payload.putArray("fields"); fields.add("summary");
	 * fields.add(STATUSFIELD); fields.add("issuetype"); fields.add("project");
	 * fields.add("priority"); fields.add(CREATEDFIELD);
	 * fields.add("description"); fields.add("resolutiondate");
	 * fields.add(storyPointsField); fields.add(sprintField);
	 * payload.put("maxResults", 100); payload.put("startAt", pagination);
	 * return payload; }
	 * 
	 * public void mapBodyObject() { Unirest.setObjectMapper(new
	 * com.mashape.unirest.http.ObjectMapper() { private final
	 * com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new
	 * com.fasterxml.jackson.databind.ObjectMapper();
	 * 
	 * public <T> T readValue(String value, Class<T> valueType) { try { return
	 * jacksonObjectMapper.readValue(value, valueType); } catch (IOException e)
	 * { log.error(String.valueOf(e)); } return null; }
	 * 
	 * public String writeValue(Object value) { try { return
	 * jacksonObjectMapper.writeValueAsString(value); } catch
	 * (JsonProcessingException e) { log.error(String.valueOf(e)); } return
	 * null; } }); }
	 * 
	 * public void sortAndSaveBySprintId(JSONArray listSprints, UserStory issue)
	 * { List<JSONObject> sortedSprints = new ArrayList<>(); for (int i = 0; i <
	 * listSprints.length(); i++) {
	 * sortedSprints.add(listSprints.getJSONObject(i)); }
	 * Collections.sort(sortedSprints, (jsonObjectA, jsonObjectB) -> { int
	 * compare = 0; try { int idA = jsonObjectA.getInt("id"); int idB =
	 * jsonObjectB.getInt("id"); compare = Integer.compare(idA, idB); } catch
	 * (JSONException e) { log.error(String.valueOf(e)); } return compare; });
	 * JSONObject currentSprint = sortedSprints.get(0); Sprint issueSprint =
	 * sprintRepository.findByJiraSprintId(currentSprint.getInt("id")); if
	 * (issueSprint != null) { issue.setSprint(issueSprint); } }
	 * 
	 * public void saveIssues(JSONArray jiraIssues, Jira foundJiraProject,
	 * String storyPointsField, String sprintField) { for (Object jiraIssue :
	 * jiraIssues) { JSONObject issueVersionedRep = ((JSONObject)
	 * jiraIssue).getJSONObject("versionedRepresentations"); if
	 * (issueVersionedRep.getJSONObject("issuetype").getJSONObject("1").
	 * getString("name").equals("Story")) { UserStory issue =
	 * userStoryRepository .findByIssueKey(String.valueOf(((JSONObject)
	 * jiraIssue).getString("key"))); if (issue == null) { issue = new
	 * UserStory(); } DateTime creationDate = new
	 * DateTime(issueVersionedRep.getJSONObject(CREATEDFIELD).getString("1"));
	 * issue.setCreationDate(creationDate.toDate()); if
	 * (issueVersionedRep.getJSONObject(STATUSFIELD).getJSONObject("1").
	 * getString("name").equals("Done")) { DateTime doneDate = new
	 * DateTime(issueVersionedRep.getJSONObject(CREATEDFIELD).getString("1"));
	 * issue.setDoneDate(doneDate.toDate()); } Object sprintDetails =
	 * (issueVersionedRep.getJSONObject(storyPointsField).get("1")); if
	 * (sprintDetails == null) { issue.setStoryPoint((Double)
	 * issueVersionedRep.getJSONObject(storyPointsField).get("1")); }
	 * issue.setIssueKey(((JSONObject) jiraIssue).getString("key"));
	 * issue.setJiraIssueId(((JSONObject) jiraIssue).getInt("id"));
	 * issue.setSummary(issueVersionedRep.getJSONObject("summary").getString("1"
	 * )); issue.setPriority(issueVersionedRep.getJSONObject("priority").
	 * getJSONObject("1").getString("name"));
	 * issue.setStatus(issueVersionedRep.getJSONObject(STATUSFIELD).
	 * getJSONObject("1").getString("name")); JSONArray listSprints =
	 * issueVersionedRep.getJSONObject(sprintField).getJSONArray("2"); if
	 * (listSprints.length() != 0) { sortAndSaveBySprintId(listSprints, issue);
	 * } issue.setJira(foundJiraProject);
	 * userStoryRepository.save(modelMapper.map(issue, UserStory.class)); } } }
	 * 
	 * public HttpResponse<JsonNode> searchInJira(ObjectNode payload) throws
	 * UnirestException { return Unirest.post(jiraUrl +
	 * "/rest/api/3/search").basicAuth(username, token).header(HEADERKEY,
	 * HEADERVALUE) .header("Content-Type", HEADERVALUE).body(payload).asJson();
	 * }
	 * 
	 * public JSONArray getJiraProjects() throws UnirestException {
	 * HttpResponse<JsonNode> jiraProjectsResponse = Unirest.get(jiraUrl +
	 * "/rest/api/3/project/") .basicAuth(username, token).header(HEADERKEY,
	 * HEADERVALUE).asJson(); return jiraProjectsResponse.getBody().getArray();
	 * }
	 * 
	 * public JSONArray getSprintList() throws UnirestException {
	 * HttpResponse<JsonNode> boards = Unirest.get(jiraUrl +
	 * "/rest/agile/1.0/board?type=scrum") .basicAuth(username,
	 * token).header(HEADERKEY, HEADERVALUE).asJson(); return
	 * boards.getBody().getObject().getJSONArray(VALUESFIELD); }
	 * 
	 * public JSONArray getJiraSprints(Long boardId) throws UnirestException {
	 * HttpResponse<JsonNode> sprints = Unirest.get(jiraUrl +
	 * "/rest/agile/1.0/board/" + boardId + "/sprint") .basicAuth(username,
	 * token).header(HEADERKEY, HEADERVALUE).asJson(); return
	 * sprints.getBody().getObject().getJSONArray(VALUESFIELD); }
	 * 
	 * public JSONArray getJiraIssueList() throws UnirestException {
	 * HttpResponse<JsonNode> jiraIssues = Unirest.get(jiraUrl +
	 * "/rest/api/3/search?jql=issuetype=Story") .basicAuth(username,
	 * token).header(HEADERKEY, HEADERVALUE).asJson(); return
	 * jiraIssues.getBody().getObject().getJSONArray("issues"); }
	 * 
	 * public String getFieldId(String fieldName) throws UnirestException,
	 * JsonProcessingException { HttpResponse<JsonNode> fieldList =
	 * Unirest.get(jiraUrl + "/rest/api/3/field?name=Sprint/")
	 * .basicAuth(username, token).header(HEADERKEY, HEADERVALUE).asJson();
	 * JSONArray fields = fieldList.getBody().getArray(); for (Object field :
	 * fields) { ObjectMapper mapper = new ObjectMapper(); FieldsDTO fieldsDTO =
	 * mapper.readValue(field.toString(), FieldsDTO.class); if
	 * (fieldsDTO.getName().equals(fieldName)) { return fieldsDTO.getId(); } }
	 * return null; }
	 */
}
