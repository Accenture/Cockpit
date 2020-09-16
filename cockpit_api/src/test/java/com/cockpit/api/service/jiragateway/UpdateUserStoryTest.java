package com.cockpit.api.service.jiragateway;

import com.cockpit.api.exception.JiraException;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.model.dto.jira.*;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.repository.SprintRepository;
import com.cockpit.api.repository.UserStoryRepository;
import com.cockpit.api.service.UserStoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateUserStoryTest {

    private UpdateUserStory updateUserStory;

    @MockBean
    private JiraApiService jiraApiService;

    @MockBean
    private UserStoryService userStoryService;

    @MockBean
    private UserStoryRepository userStoryRepository;

    @MockBean
    private SprintRepository sprintRepository;

    @MockBean
    private JiraRepository jiraRepository;


    @Value("${spring.jira.urlIssues}")
    private String urlIssues;
    @Value("${spring.jira.urlAllUserStories}")
    private String urlAllUserStories;

    @Before
    public void setUp() {
        this.updateUserStory = new UpdateUserStory(
                jiraRepository,
                sprintRepository,
                userStoryRepository,
                userStoryService,
                jiraApiService);
        ReflectionTestUtils.setField(updateUserStory, "urlIssues", urlIssues);
        ReflectionTestUtils.setField(updateUserStory, "urlAllUserStories", urlAllUserStories);
    }

    @Test
    public void whenUpdateUserStoriesThenUserStoriesUpdated() throws JiraException {
        Issues mockIssues = new Issues();
        mockIssues.setTotal(1);
        Issue mockIssue = new Issue();
        mockIssue.setId("1");
        Fields mockFields = new Fields();
        mockFields.setSummary("test_field_summary");
        Status mockStatus = new Status();
        mockStatus.setName("BACKLOG");
        mockFields.setStatus(mockStatus);
        Project mockProject = new Project();
        mockProject.setId("1");
        mockFields.setProject(mockProject);
        Priority mockPriority = new Priority();
        mockPriority.setName("HIGH");
        mockFields.setPriority(mockPriority);
        List<Issue> mockIssueList = new ArrayList<>();
        mockIssue.setFields(mockFields);
        mockIssueList.add(mockIssue);
        mockIssues.setJiraIssues(mockIssueList);
        ResponseEntity mockResponse = new ResponseEntity(mockIssues,HttpStatus.OK);

        Jira mockJira = new Jira();
        mockJira.setId(1l);
        mockJira.setJiraProjectKey("TEST_KEY");
        mockJira.setBoardId(1);
        List<Jira> mockJiraList = new ArrayList<>();
        mockJiraList.add(mockJira);
        Sprint mockSprint = new Sprint();
        mockSprint.setJira(mockJira);
        mockSprint.setJiraSprintId(1);
        mockSprint.setId(1l);
        List<Sprint> mockSprintList = new ArrayList<>();
        mockSprintList.add(mockSprint);

        // given
        Mockito.when(jiraApiService.callJira(urlIssues + "Sprint=1 AND issuetype=Story&expand=changelog", Issues.class.getName())).thenReturn(mockResponse);
        Mockito.when(jiraApiService.callJira(urlIssues + "project=TEST_KEY AND Sprint=null AND issuetype=Story&expand=changelog", Issues.class.getName())).thenReturn(mockResponse);
        Mockito.when(jiraRepository.findAllByOrderById()).thenReturn(mockJiraList);
        Mockito.when(sprintRepository.findAllByOrderById()).thenReturn(mockSprintList);

        // when
        updateUserStory.updateUserStoryInDBFromJira();

        // then
        verify(userStoryRepository, atLeastOnce()).save(Mockito.any(UserStory.class));

    }

    @Test
    public void whenClearUserStoriesThenUserStoriesCleaned() throws JiraException {
        Issues mockIssues = new Issues();
        mockIssues.setTotal(1);
        Issue mockIssue = new Issue();
        mockIssue.setId("1");
        Fields mockFields = new Fields();
        mockFields.setSummary("test_field_summary");
        Status mockStatus = new Status();
        mockStatus.setName("BACKLOG");
        mockFields.setStatus(mockStatus);
        Project mockProject = new Project();
        mockProject.setId("1");
        mockFields.setProject(mockProject);
        Priority mockPriority = new Priority();
        mockPriority.setName("HIGH");
        mockFields.setPriority(mockPriority);
        List<Issue> mockIssueList = new ArrayList<>();
        mockIssue.setFields(mockFields);
        mockIssueList.add(mockIssue);
        mockIssues.setJiraIssues(mockIssueList);
        ResponseEntity mockResponse = new ResponseEntity(mockIssues,HttpStatus.OK);

        int maxResults = 100;
        int startAt = 0;
        String testUrl = String.format(urlAllUserStories, maxResults, startAt);

        // given
        Mockito.when(jiraApiService.callJira(testUrl, Issues.class.getName())).thenReturn(mockResponse);

        // when
        updateUserStory.cleaningUselessUSFromDB();

        // then
        verify(userStoryRepository, atLeastOnce()).deleteAllByIssueKeyNotIn(Mockito.anyList());

    }
}
