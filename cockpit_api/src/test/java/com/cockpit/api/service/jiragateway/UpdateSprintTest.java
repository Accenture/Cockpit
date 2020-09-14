package com.cockpit.api.service.jiragateway;

import com.cockpit.api.exception.JiraException;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
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

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateSprintTest {

    private UpdateSprint updateSprint;

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


    @Value("${spring.jira.urlSprints}")
    private String urlSprints;
    @Value("${spring.jira.urlSprintReport}")
    private String urlSprintReport;

    @Before
    public void setUp() {
        this.updateSprint = new UpdateSprint(
                jiraRepository,
                sprintRepository,
                userStoryRepository,
                userStoryService,
                jiraApiService);
        ReflectionTestUtils.setField(updateSprint, "urlSprints", urlSprints);
        ReflectionTestUtils.setField(updateSprint, "urlSprintReport", urlSprintReport);
    }

    @Test
    public void whenUpdateSprintsThenSprintsUpdated() throws JiraException {
        SprintJira mockSprintJira = new SprintJira();
        mockSprintJira.setId(10086);
        mockSprintJira.setState("active");
        List<SprintJira> mockSprintJiraList = new ArrayList<>();
        mockSprintJiraList.add(mockSprintJira);

        SprintHeaders mockSprintHeaders = new SprintHeaders();
        mockSprintHeaders.setValues(mockSprintJiraList);

        ResponseEntity mockResponse = new ResponseEntity(mockSprintHeaders,HttpStatus.OK);

        Jira mockJira = new Jira();
        mockJira.setId(1l);
        mockJira.setJiraProjectKey("TEST_KEY");
        mockJira.setBoardId(1);
        List<Jira> mockJiraList = new ArrayList<>();
        mockJiraList.add(mockJira);
        Sprint mockSprint = new Sprint();
        mockSprint.setJira(mockJira);
        mockSprint.setId(1l);
        List<Sprint> mockSprintList = new ArrayList<>();
        mockSprintList.add(mockSprint);

        // given
        Mockito.when(jiraApiService.callJira(urlSprints + "1/sprint", SprintHeaders.class.getName())).thenReturn(mockResponse);
        Mockito.when(jiraRepository.findAllByOrderById()).thenReturn(mockJiraList);
        Mockito.when(sprintRepository.findByJiraOrderBySprintNumber(Mockito.any())).thenReturn(mockSprintList);

        // when
        updateSprint.updateSprintsForEachProject();

        // then
        verify(sprintRepository, atLeastOnce()).save(Mockito.any(Sprint.class));

    }

    @Test
    public void whenSetTotalNbOfUserStoryForEachSprintOfEachProjectThenTotalNbUpdated() {
        Jira mockJira = new Jira();
        mockJira.setId(1l);
        mockJira.setJiraProjectKey("TEST_KEY_JIRA");
        mockJira.setBoardId(1);
        List<Jira> mockJiraList = new ArrayList<>();
        mockJiraList.add(mockJira);

        Sprint mockSprint = new Sprint();
        mockSprint.setJira(mockJira);

        Date today = new Date();
        mockSprint.setId(1l);
        mockSprint.setSprintStartDate(today);
        mockSprint.setSprintEndDate(today);
        mockSprint.setSprintCompleteDate(today);

        List<Sprint> mockSprintList = new ArrayList<>();
        mockSprintList.add(mockSprint);

        // given
        Mockito.when(jiraRepository.findAllByOrderById()).thenReturn(mockJiraList);
        Mockito.when(sprintRepository.findByJiraOrderBySprintNumber(Mockito.any())).thenReturn(mockSprintList);
        Mockito.when(userStoryRepository.countUserStoriesByJiraAndCreationDateBefore(Mockito.any(), Mockito.any())).thenReturn(2);

        // when
        updateSprint.setTotalNbOfUserStoryForEachSprintOfEachProject();

        // then
        verify(sprintRepository, atLeastOnce()).save(Mockito.any(Sprint.class));
    }

    @Test
    public void whenUpdateSumForCompletedIssuesAndSumForNotCompletedIssuesInSprintThenSprintUpdated() throws JiraException {
        Jira mockJira = new Jira();
        mockJira.setId(1l);
        mockJira.setJiraProjectKey("TEST");
        mockJira.setBoardId(1);
        List<Jira> mockJiraList = new ArrayList<>();
        mockJiraList.add(mockJira);

        Date today = new Date();
        Sprint mockSprint = new Sprint();
        mockSprint.setJira(mockJira);
        mockSprint.setId(1l);
        mockSprint.setJiraSprintId(1);
        mockSprint.setSprintStartDate(today);
        mockSprint.setSprintEndDate(today);
        mockSprint.setSprintCompleteDate(today);
        List<Sprint> mockSprintList = new ArrayList<>();
        mockSprintList.add(mockSprint);

        SprintReport mockSprintReport = new SprintReport();
        SprintReportContent mockContent = new SprintReportContent();
        SprintReportIssue mockSprintReportIssue = new SprintReportIssue();
        mockSprintReportIssue.setId(1l);
        mockSprintReportIssue.setTypeName("Story");
        List<SprintReportIssue> completedIssuesList = new ArrayList();
        List<SprintReportIssue> emptyList = new ArrayList();
        completedIssuesList.add(mockSprintReportIssue);
        mockContent.setIssuesNotCompletedInCurrentSprint(emptyList);
        mockContent.setPuntedIssues(emptyList);
        mockContent.setCompletedIssues(completedIssuesList);
        mockSprintReport.setContents(mockContent);

        ResponseEntity mockResponse = new ResponseEntity(mockSprintReport,HttpStatus.OK);

        // given
        Mockito.when(jiraApiService.callJira(urlSprintReport + "rapidViewId=1&sprintId=1", SprintReport.class.getName())).thenReturn(mockResponse);
        Mockito.when(jiraRepository.findAllByOrderById()).thenReturn(mockJiraList);
        Mockito.when(sprintRepository.findByJiraOrderBySprintNumber(Mockito.any())).thenReturn(mockSprintList);
        // when
        updateSprint.updateSumForCompletedIssuesAndSumForNotCompletedIssuesInSprint();

        // then
        verify(sprintRepository, atLeastOnce()).save(Mockito.any(Sprint.class));
    }
}
