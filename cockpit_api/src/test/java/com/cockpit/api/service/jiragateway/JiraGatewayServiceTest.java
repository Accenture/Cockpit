package com.cockpit.api.service.jiragateway;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.repository.SprintRepository;
import com.cockpit.api.repository.UserStoryRepository;
import com.cockpit.api.service.UserStoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


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
        Jira notFoundJira = new Jira();
        Sprint mockSprint = new Sprint();
        Sprint notFoundSprint = new Sprint();
        UserStory mockUserStory = new UserStory();
        UserStory notFoundUserStory = new UserStory();

        mockJira.setId(99999L);
        mockJira.setJiraProjectKey("TEST");
        notFoundJira.setId(99998L);
        notFoundJira.setJiraProjectKey("NU");

        mockSprint.setJira(mockJira);
        mockSprint.setId(99999L);
        mockSprint.setJiraSprintId(10024);
        mockSprint.setSprintNumber(2);
        notFoundSprint.setJira(mockJira);
        notFoundSprint.setId(99998L);
        notFoundSprint.setJiraSprintId(10025);
        notFoundSprint.setSprintNumber(2);

        mockUserStory.setJira(mockJira);
        mockUserStory.setId(99999L);
        mockUserStory.setSprint(mockSprint);
        mockUserStory.setIssueKey("TC");
        notFoundUserStory.setJira(mockJira);
        notFoundUserStory.setId(99998L);
        notFoundUserStory.setSprint(mockSprint);
        notFoundUserStory.setIssueKey("NU");

        List<UserStory> mockUserStoryList = new ArrayList<>();
        mockUserStoryList.add(mockUserStory);
        List<UserStory> notFoundUserStoryList = new ArrayList<>();
        notFoundUserStoryList.add(notFoundUserStory);

        List<Sprint> mockSprintList = new ArrayList<>();
        mockSprintList.add(mockSprint);
        List<Sprint> notFoundSprintList = new ArrayList<>();
        notFoundSprintList.add(notFoundSprint);

        List<Jira> mockJiraList = new ArrayList<>();
        mockJiraList.add(mockJira);
        List<Jira> notFoundJiraList = new ArrayList<>();
        notFoundJiraList.add(notFoundJira);

        // TEST JIRA DELETE SCHEDULED TASK
        JSONArray notFoundJiraJsonArray = new JSONArray();
        for (Jira jira : notFoundJiraList) {
            notFoundJiraJsonArray.put(jira);
        }
        JiraGatewayService mockJiraGatewayService = mock(JiraGatewayService.class);
        ReflectionTestUtils.setField(jiraGatewayService, "username", username);
        ReflectionTestUtils.setField(jiraGatewayService, "token", token);
        ReflectionTestUtils.setField(jiraGatewayService, "jiraUrl", jiraUrl);
        Mockito.when(jiraRepository.findAllByOrderById()).thenReturn(mockJiraList);
        Mockito.when(mockJiraGatewayService.getJiraProjects()).thenReturn(notFoundJiraJsonArray);
        jiraGatewayService.deleteJiraProjects();
        verify(jiraRepository, atLeastOnce()).delete(mockJira);

        // TEST SPRINT DELETE SCHEDULED TASK
        Mockito.when(sprintRepository.findAll()).thenReturn(mockSprintList);
        JSONArray notFoundSprintJsonArray = new JSONArray();
        for (Sprint sprint : notFoundSprintList) {
            notFoundSprintJsonArray.put(sprint);
        }
        Mockito.when(mockJiraGatewayService.getJiraSprints(anyLong())).thenReturn(notFoundSprintJsonArray);
        jiraGatewayService.deleteJiraSprint();
        verify(sprintRepository, atLeastOnce()).delete(mockSprint);

        // TEST USER STORY DELETE SCHEDULED TASK
        Mockito.when(userStoryRepository.findAll()).thenReturn(mockUserStoryList);
        JSONArray notFoundUserStoryJsonArray = new JSONArray();
        for (UserStory userStory : notFoundUserStoryList) {
            notFoundUserStoryJsonArray.put(userStory);
        }
        Mockito.when(mockJiraGatewayService.getJiraIssueList()).thenReturn(notFoundUserStoryJsonArray);
        jiraGatewayService.deleteJiraIssues();
        verify(userStoryRepository, atLeastOnce()).delete(mockUserStory);

    }
}
