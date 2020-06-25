package com.cockpit.api.service.jiragateway;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.repository.SprintRepository;
import com.cockpit.api.repository.UserStoryRepository;
import com.cockpit.api.service.UserStoryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class JiraGatewayServiceTest {

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
    public void setUp() {
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
    public void whenDeleteOnJira_thenDeleteOnCockpit(){
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

        jiraRepository.save(mockJira);
        sprintRepository.save(mockSprint);
        userStoryRepository.save(mockUserStory);

        Optional<Jira> foundJira = jiraRepository.findById(mockJira.getId());

        Optional<Sprint> foundSprint = sprintRepository.findById(mockSprint.getId());

        Optional<UserStory> foundUserStory = userStoryRepository.findById(mockUserStory.getId());


        List<UserStory> mockUserStoryList = new ArrayList<>();
        foundUserStory.ifPresent(mockUserStoryList::add);

        List<Sprint> mockSprintList = new ArrayList<>();
        foundSprint.ifPresent(mockSprintList::add);

        List<Jira> mockJiraList = new ArrayList<>();
        foundJira.ifPresent(mockJiraList::add);

        Mockito.when(userStoryRepository.findAll()).thenReturn(mockUserStoryList);
        userStoryRepository.delete(mockUserStory);
        Mockito.when(sprintRepository.findAll()).thenReturn(mockSprintList);
        sprintRepository.delete(mockSprint);
        Mockito.when(jiraRepository.findAllByOrderById()).thenReturn(mockJiraList);
        jiraRepository.delete(mockJira);
        assertThat(jiraRepository.count()).isEqualTo(0);
    }

}
