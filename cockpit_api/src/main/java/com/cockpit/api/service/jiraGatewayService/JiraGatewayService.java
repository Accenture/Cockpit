package com.cockpit.api.service.jiraGatewayService;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.repository.SprintRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
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


import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;

import static com.jayway.jsonpath.JsonPath.parse;

@Configuration
@EnableScheduling
@Service
public class JiraGatewayService {

    private ModelMapper modelMapper = new ModelMapper();

    private final JiraRepository jiraRepository;
    private final SprintRepository sprintRepository;

    @Autowired
    public JiraGatewayService(JiraRepository jiraRepository, SprintRepository sprintRepository) {
        this.jiraRepository = jiraRepository;
        this.sprintRepository = sprintRepository;
    }

    static Logger log = LoggerFactory.getLogger(JiraGatewayService.class);

    @Value("${spring.jira.username}")
    private String username;
    @Value("${spring.jira.token}")
    private String token;
    @Value("${spring.jira.jiraUrl}")
    private String jiraUrl;
    @Value("${spring.jira.encodedToken}")
    private String authHeader;

    @Scheduled(fixedRate = 60000 * 60)
    public void updateProjects() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(jiraUrl+"/rest/api/3/project/search")
                .basicAuth(username, token)
                .header("Accept", "application/json")
                .asJson();
        JSONArray jiraProjects = response.getBody().getObject().getJSONArray("values");
        for (Object jiraProject: jiraProjects){
            if (jiraProject instanceof JSONObject){
                parse(((JSONObject) jiraProject).getString("name"));
                Jira foundJiraProject = jiraRepository.findByJiraProjectKey(String.valueOf(((JSONObject) jiraProject).getString("key")));
                if (foundJiraProject != null){
                    foundJiraProject.setJiraProjectId(((JSONObject) jiraProject).getInt("id"));
                    jiraRepository.save(modelMapper.map(foundJiraProject, Jira.class));
                    log.info("Jira projects' keys updated");
                }

            }
        }
    }

    @Scheduled(fixedRate = 10000)
    public void getJiraSprints() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(jiraUrl+"/rest/agile/1.0/board?type=scrum")
                .header("Authorization", "Basic "+authHeader)
                .header("Accept", "application/json")
                .asJson();
        JSONArray jiraBoards = response.getBody().getObject().getJSONArray("values");
        for (Object jiraBoard: jiraBoards){
            if (jiraBoard instanceof JSONObject){
                parse(((JSONObject) jiraBoard).getString("name"));
                Jira foundJiraProject = jiraRepository.findByJiraProjectKey(String.valueOf(((JSONObject) jiraBoard).getJSONObject("location").getString("projectKey")));
                String boardId = String.valueOf(((JSONObject) jiraBoard).getInt("id"));
                HttpResponse<JsonNode> sprintResponse = Unirest.get(jiraUrl+"/rest/agile/1.0/board/"+boardId+"/sprint")
                        .header("Authorization", "Basic "+authHeader)
                        .header("Accept", "application/json")
                        .asJson();
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
                            log.warn(String.valueOf(foundJiraProject));
                            sprint.setJiraSprintId(((JSONObject)jiraSprint).getInt("id"));
                            sprint.setSprintNumber(counter);
                            sprint.setJira(foundJiraProject);
                            sprint.setState(sprintState);
                            if(sprintState.equals("active")){
                                Calendar startDate = DatatypeConverter.parseDateTime(((JSONObject)jiraSprint).getString("startDate"));
                                sprint.setSprintStartDate(startDate.getTime());
                                Calendar endDate = DatatypeConverter.parseDateTime(((JSONObject)jiraSprint).getString("endDate"));
                                sprint.setSprintEndDate(endDate.getTime());
                            }else if(sprintState.equals("closed")){
                                Calendar startDate = DatatypeConverter.parseDateTime(((JSONObject)jiraSprint).getString("startDate"));
                                sprint.setSprintStartDate(startDate.getTime());
                                Calendar endDate = DatatypeConverter.parseDateTime(((JSONObject)jiraSprint).getString("endDate"));
                                sprint.setSprintEndDate(endDate.getTime());
                                Calendar completeDate = DatatypeConverter.parseDateTime(((JSONObject)jiraSprint).getString("completeDate"));
                                sprint.setSprintCompleteDate(completeDate.getTime());
                            }
                            sprintRepository.save(modelMapper.map(sprint, Sprint.class));
                            log.info("Jira projects' sprints updated");
                            jiraRepository.save(modelMapper.map(foundJiraProject, Jira.class));
                            log.info("Jira projects' current sprint updated");
                        }

                        parse(((JSONObject)jiraSprint).getString("name"));
                    }
                }

            }
        }
    }

}
