package com.cockpit.api.service.jiragateway;

import com.cockpit.api.exception.HttpException;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dto.jira.Board;
import com.cockpit.api.model.dto.jira.JiraBoard;
import com.cockpit.api.model.dto.jira.Location;
import com.cockpit.api.model.dto.jira.Project;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.service.HttpService;
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
import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UpdateJira.class, UserStoryService.class})
public class UpdateJiraTest {

    private UpdateJira updateJira;

    @MockBean
    private HttpService httpService;

    @MockBean
    private UserStoryService userStoryService;

    @MockBean
    private JiraRepository jiraRepository;


    @Value("${spring.jira.urlProjects}")
    private String urlProjects;
    @Value("${spring.jira.urlBoards}")
    private String urlBoards;

    @Before
    public void setUp() {
        this.updateJira = new UpdateJira(jiraRepository, httpService, userStoryService);
        ReflectionTestUtils.setField(updateJira, "urlProjects", urlProjects);
        ReflectionTestUtils.setField(updateJira, "urlBoards", urlBoards);
    }

    @Test
    public void whenUpdateJiraProjectIdThenProjectIdUpdated() throws HttpException {

        Project mockProject = new Project();
        mockProject.setId("100");
        mockProject.setKey("TEST_KEY");
        Project[] mockProjectList = new Project[1];
        mockProjectList[0] = mockProject;
        ResponseEntity mockResponse = new ResponseEntity(mockProjectList,HttpStatus.OK);

        Jira mockJira = new Jira();
        mockJira.setJiraProjectKey("TEST_KEY");
        List<Jira> mockJiraList = new ArrayList<>();
        mockJiraList.add(mockJira);

        // given
        Mockito.when(httpService.httpCall(urlProjects, Project[].class.getName())).thenReturn(mockResponse);
        Mockito.when(jiraRepository.findAllByOrderById()).thenReturn(mockJiraList);

        // when
        updateJira.updateProjectIdInJira();

        // then
        verify(jiraRepository, atLeastOnce()).save(mockJira);

    }

    @Test
    public void whenUpdateJiraBoardIdThenBoardIdUpdated() throws HttpException {
        List<JiraBoard> boardList = new ArrayList<>();
        JiraBoard mockJiraBoard = new JiraBoard();
        Location mockLocation = new Location();
        mockLocation.setProjectId(1000);
        mockJiraBoard.setLocation(mockLocation);
        boardList.add(mockJiraBoard);

        Board mockBoard = new Board();
        mockBoard.setStartAt(0);
        mockBoard.setTotal(1);
        mockBoard.setMaxResults(1);
        mockBoard.setValues(boardList);

        ResponseEntity mockResponse = new ResponseEntity(mockBoard,HttpStatus.OK);

        Jira mockJira = new Jira();
        mockJira.setJiraProjectKey("TEST_KEY");
        mockJira.setJiraProjectId(10);
        mockJira.setBoardId(1000);

        int maxResults = 50;
        int startAt = 0;
        String url = String.format(urlBoards, maxResults, startAt);

        // given
        Mockito.when(httpService.httpCall(url, Board.class.getName())).thenReturn(mockResponse);
        Mockito.when(jiraRepository.findByJiraProjectId(Mockito.anyInt())).thenReturn(mockJira);

        // when
        updateJira.updateBoardIdInJira();

        // then
        verify(jiraRepository, atLeastOnce()).findByJiraProjectId(Mockito.anyInt());
    }

}
