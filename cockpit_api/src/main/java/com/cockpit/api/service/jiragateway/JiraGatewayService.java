package com.cockpit.api.service.jiragateway;

import static javax.management.timer.Timer.ONE_HOUR;
import static javax.management.timer.Timer.ONE_MINUTE;
import static javax.management.timer.Timer.ONE_SECOND;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.model.dto.jira.*;
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
    private List<UserStory> stories = new ArrayList<>();
    HttpEntity<String> request;
    HttpHeaders headers;
    RestTemplate restTemplate = new RestTemplate();
    private final SprintRepository sprintRepository;
    private final JiraRepository jiraRepository;
    private final UserStoryRepository userStoryRepository;
    final UserStoryService userStoryService;

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
    @Value("${spring.jira.urlIssues}")
    private String urlIssues;
    @Value("${spring.jira.urlAllIssues}")
    private String urlAllIssues;

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
            throw new Exception("Exception Call jira for url" + url);
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
        } catch (Exception e) {
        }
    }

    @Scheduled(initialDelay = 10 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    public void setTotalNbOfUserStoryForEachSprintOfEachProject() {
        List<Jira> jiraProjectList = jiraRepository.findAllByOrderById();
        for
        (Jira jira : jiraProjectList) {
            List<Sprint> sprintList =
                    sprintRepository.findByJiraOrderBySprintNumber(jira);
            int
                    totalNumberOfUserStoriesUntilCurrentSprint = 0;
            if (sprintList.size() >
                    0) {
                totalNumberOfUserStoriesUntilCurrentSprint = userStoryRepository
                        .countUserStoriesByJiraAndCreationDateBefore(jira,
                                sprintList.get(0).getSprintStartDate());
            }
            for (Sprint sprint :
                    sprintList) {
                Date sprintStartDate = sprint.getSprintStartDate();
                Date sprintEndDate = sprint.getSprintEndDate();
                if (sprintStartDate != null &&
                        sprintEndDate != null) {
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

    @Scheduled(initialDelay = 5 * ONE_SECOND, fixedDelay = ONE_HOUR)
    public void updateUserStoryInDBFromJira() {
        List<Sprint> sprintList;
        try {
            sprintList = sprintRepository.findAll();
            for (Sprint sprint : Optional.ofNullable(sprintList).orElse(Collections.emptyList())) {
                updateUserStoryInDBForASprintFromJira(sprint, urlIssues);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Scheduled(initialDelay = 30 * ONE_SECOND, fixedDelay = ONE_HOUR)
    public void cleaningUselessUSFromDB() {
        log.info("UserStory - Start cleaning useless user stories - Thread : " + Thread.currentThread().getName());
        try{
            cleanUserStoriesNotLongerExists(urlAllIssues);
        }
        catch (Exception e) {
            log.error("Exception thrown when trying to delete user stories in DB not present in JIRA");
            log.debug(e.getMessage());
        }
        log.info("UserStory - End cleaning useless user stories - Thread : " + Thread.currentThread().getName());
    }

    public void cleanUserStoriesNotLongerExists(String urlIssues) throws Exception {
        List<Issue> issueList = new ArrayList<Issue>();
        List<UserStory> userStoryList = null;
        int maxResults = 100;
        int startAt = 0;
        int totalValues = 0;
        int i = 1;
        while (totalValues >= startAt) {
            String url = String.format(urlIssues, maxResults, startAt);
            ResponseEntity<Issues> result = (ResponseEntity<Issues>) callJira(url, Issues.class.getName());
            if (result.getStatusCode().is2xxSuccessful()) {
                totalValues = result.getBody().getTotal();
                startAt = (maxResults * i) + 1;
                issueList.addAll(result.getBody().getIssues());
                i++;
            } else {
                throw new Exception("Unable to get Issues From Jira");
            }
        }
        try {
            clearDBFromDifferenceInUserStories(issueList);
        }
        catch (Exception e) {
            log.error("UserStory - Unable to clean user stories that no longer exists in JIRA");
            log.error(e.getMessage());
        }
    }

    private void clearDBFromDifferenceInUserStories(List<Issue> issueList){

        if(!issueList.isEmpty()){
            List<String> newIssues = issueList.stream().map(Issue::getKey).collect(Collectors.toList());
            userStoryRepository.deleteAllByIssueKeyNotIn(newIssues);
        }

    }

    public List<UserStory> updateUserStoryInDBForASprintFromJira(Sprint sprint, String urlIssues) throws Exception {

        String sprintId = String.valueOf(sprint.getJiraSprintId());
        String jql = "Sprint=" + sprintId + " AND issuetype=Story&expand=changelog";
        String url = urlIssues + jql;
        ResponseEntity<Issues> result = (ResponseEntity<Issues>) callJira(url, Issues.class.getName());
        List<Issue> issueList = (result.getBody().getIssues());
        if (result.getStatusCode().is2xxSuccessful()) {
            return getUserStories(sprint, issueList);
        }
        return stories;
    }
    private List<UserStory> getUserStories(Sprint sprint, List<Issue> issueList) {
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
                userStory.setJira(jiraRepository.findByJiraProjectId(Integer.parseInt(issue.getFields().getProject().getId())).get());
                userStory.setPriority((issue.getFields().getPriority() == null) ? "N/A" : issue.getFields().getPriority().getName());
                userStory.setStoryPoint((issue.getFields().getCustomfield10026() == null) ? 0 : (double) issue.getFields().getCustomfield10026());
                try {
                    userStory.setCreationDate((issue.getFields().getCreated() == null) ? null : dateFormat.parse(issue.getFields().getCreated()));
                } catch (ParseException e) {
                    log.error("Exception thrown when parsing updateDate for UserStory Status");
                }
            }
            stories.add(userStory);
            userStoryRepository.save(userStory);
        }
        return stories;
    }

    public void cleanWrongSprintFromJira(List<Sprint> sprints) {
        if (sprints != null && !sprints.isEmpty()) {
            for (Sprint sprint : sprints) {

                sprintRepository.delete(sprint);
            }
            sprints.clear();
        }
    }

    public void getSprintsToRemove(List<SprintJira>  sprintListJira, List<Sprint> sprintList) {
        for (Sprint sprint: sprintList) {
            if (sprintListJira.stream().filter(sprintJira -> sprint.getJiraSprintId() == sprintJira.getId()).count() == 0 ) {
                sprintsToRemove.add(sprint);
            }
        }
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
