package com.cockpit.api.service.jiragateway;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.repository.SprintRepository;
import com.cockpit.api.repository.UserStoryRepository;
import com.cockpit.api.service.UserStoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class JiraGatewayServiceTest {

    @Value("${spring.jira.username}")
    private String username;
    @Value("${spring.jira.token}")
    private String token;
    @Value("${spring.jira.jiraUrl}")
    private String jiraUrl;


    private JiraGatewayService jiraGatewayService;

    @MockBean
    private JiraRepository jiraRepository;

    @MockBean
    private UserStoryRepository userStoryRepository;

    @MockBean
    private SprintRepository sprintRepository;

    @MockBean
    private UserStoryService userStoryService;

    private static final Date SPRINT_START_DATE = new Date();
    private static final Date SPRINT_END_DATE = new Date(SPRINT_START_DATE.getTime() + 1000 * 60 * 60 * 24 * 14);

    @Before
    public void setUp() throws JsonProcessingException, UnirestException {
        this.jiraGatewayService = new JiraGatewayService(jiraRepository, sprintRepository, userStoryRepository, userStoryService);
    }


    @Test
    public void whenUpdateTotalNbUserStoriesTaskRuns_thenUpdateTotalNbUserStoriesInSprint() {
        // Given
        Jira mockJira = new Jira();
        Sprint mockSprint = new Sprint();

        mockJira.setId(1l);
        mockJira.setJiraProjectKey("TEST");
        mockSprint.setJira(mockJira);
        mockSprint.setSprintNumber(1);
        mockSprint.setSprintStartDate(SPRINT_START_DATE);
        mockSprint.setSprintEndDate(SPRINT_END_DATE);

        List<Jira> mockJiraList = new ArrayList<>();
        mockJiraList.add(mockJira);
        List<Sprint> mockSprintList = new ArrayList<>();
        mockSprintList.add(mockSprint);
        Mockito.when(jiraRepository.findAllByOrderById()).thenReturn(mockJiraList);
        Mockito.when(sprintRepository.findByJiraOrderBySprintNumber(Mockito.any(Jira.class))).thenReturn(mockSprintList);
        Mockito.when(userStoryRepository.countUserStoriesByJiraAndCreationDateBefore(Mockito.any(Jira.class), Mockito.any(Date.class))).thenReturn(2);
        Mockito.when(userStoryRepository.countUserStoriesByJiraAndCreationDateGreaterThanAndCreationDateLessThanEqual(Mockito.any(Jira.class), Mockito.any(Date.class),  Mockito.any(Date.class))).thenReturn(3);

//      when
        jiraGatewayService.setTotalNbOfUserStoryForEachSprintOfEachProject();

        // then
        Assert.assertEquals(5, mockSprint.getTotalNbUs().intValue());
    }

    @Test
    public void whenDeleteOnJira_thenDeleteOnCockpit() throws UnirestException {
        Jira mockJira = new Jira();
        Sprint mockSprint = new Sprint();

        mockJira.setId(99999L);
        mockJira.setJiraProjectKey("TEST");

        mockSprint.setJira(mockJira);
        mockSprint.setId(99999L);
        mockSprint.setJiraSprintId(10024);
        mockSprint.setSprintNumber(2);

        UserStory mockUserStory = new UserStory();

        mockUserStory.setJira(mockJira);
        mockUserStory.setId(99999L);
        mockUserStory.setSprint(mockSprint);


        List<UserStory> mockUserStoryList = new ArrayList<>();
        mockUserStoryList.add(mockUserStory);

        List<Sprint> mockSprintList = new ArrayList<>();
        mockSprintList.add(mockSprint);

        List<Jira> mockJiraList = new ArrayList<>();
        mockJiraList.add(mockJira);
        JSONArray mockJiraJsonList = new JSONArray(mockJiraList);

        Mockito.when(jiraRepository.findAllByOrderById()).thenReturn(mockJiraList);
//        Mockito.when(Unirest.get(jiraUrl+"/rest/api/3/project/")
//                .basicAuth(username, token)
//                .header("Accept", "application/json")
//                .asJson()).thenReturn();

//        jiraGatewayService.deleteJiraProjects();

//        Mockito.when(userStoryRepository.findAll()).thenReturn(mockUserStoryList);
//        userStoryRepository.delete(mockUserStory);
//
//        Mockito.when(sprintRepository.findAll()).thenReturn(mockSprintList);
//        sprintRepository.delete(mockSprint);
//
//

        assertThat(jiraRepository.count()).isEqualTo(0);

    }



}
