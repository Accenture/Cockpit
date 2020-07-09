package com.cockpit.api.service.jiragateway;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.repository.SprintRepository;
import com.cockpit.api.repository.UserStoryRepository;
import com.cockpit.api.service.UserStoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = JiraGatewayService.class)
public class JiraGatewayServiceTest {

    @MockBean
    private JiraRepository jiraRepository;

    @MockBean
    private UserStoryRepository userStoryRepository;

    @MockBean
    private SprintRepository sprintRepository;

    @MockBean
    private UserStoryService userStoryService;

    private JiraGatewayService jiraGatewayService;

    @Value("${spring.jira.username}")
    private String username;
    @Value("${spring.jira.token}")
    private String token;
    @Value("${spring.jira.jiraUrl}")
    private String jiraUrl;

    static Logger log = LoggerFactory.getLogger(JiraGatewayService.class);

    private static final Date SPRINT_START_DATE = new Date();
    private static final Date SPRINT_END_DATE = new Date(SPRINT_START_DATE.getTime() + 1000 * 60 * 60 * 24 * 14);

    @Before
    public void setUp() {
        this.jiraGatewayService = new JiraGatewayService(jiraRepository, sprintRepository, userStoryRepository, userStoryService);
    }


    @Test
    public void whenUpdateTotalNbUserStoriesTaskRunsThenUpdateTotalNbUserStoriesInSprint() {
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

        // when
        jiraGatewayService.setTotalNbOfUserStoryForEachSprintOfEachProject();

        // then
        Assert.assertEquals(5, mockSprint.getTotalNbUs().intValue());
    }

    @Test
    public void whenDeleteOnJiraThenDeleteOnCockpit() throws UnirestException, JsonProcessingException, JSONException {
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
        mockUserStory.setIssueKey("TC");


        List<UserStory> mockUserStoryList = new ArrayList<>();
        mockUserStoryList.add(mockUserStory);
        mockUserStoryList.add(mockUserStory);

        List<Sprint> mockSprintList = new ArrayList<>();
        mockSprintList.add(mockSprint);
        mockSprintList.add(mockSprint);

        List<Jira> mockJiraList = new ArrayList<>();
        mockJiraList.add(mockJira);
        mockJiraList.add(mockJira);

        Mockito.when(jiraRepository.findAllByOrderById()).thenReturn(mockJiraList);
        JSONArray mockJiraJsonArray = new JSONArray();
        for (Jira jira : mockJiraList) {
            mockJiraJsonArray.put(jira);
        }

        JiraGatewayService mockJiraGatewayService = mock(JiraGatewayService.class);
        ReflectionTestUtils.setField(jiraGatewayService, "username", username);
        ReflectionTestUtils.setField(jiraGatewayService, "token", token);
        ReflectionTestUtils.setField(jiraGatewayService, "jiraUrl", jiraUrl);

        Mockito.when(mockJiraGatewayService.getJiraProjects()).thenReturn(mockJiraJsonArray);
        jiraGatewayService.deleteJiraProjects();

        Mockito.when(sprintRepository.findAll()).thenReturn(mockSprintList);
        JSONArray mockSprintJsonArray = new JSONArray();
        for (Sprint sprint : mockSprintList) {
            mockSprintJsonArray.put(sprint);
        }
        Mockito.when(mockJiraGatewayService.getJiraSprints(anyLong())).thenReturn(mockSprintJsonArray);
        jiraGatewayService.deleteJiraSprint();

        Mockito.when(userStoryRepository.findAll()).thenReturn(mockUserStoryList);
        JSONArray mockUserStoryJsonArray = new JSONArray();
        for (UserStory userStory : mockUserStoryList) {
            mockUserStoryJsonArray.put(userStory);
        }
        Mockito.when(mockJiraGatewayService.getJiraIssueList()).thenReturn(mockUserStoryJsonArray);
        jiraGatewayService.deleteJiraIssues();

        assertThat(userStoryRepository.count()).isEqualTo(0);
        assertThat(sprintRepository.count()).isEqualTo(0);
        assertThat(jiraRepository.count()).isEqualTo(0);

    }



}
