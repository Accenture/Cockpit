package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.BurnUpChartDTO;
import com.cockpit.api.model.dto.MvpDTO;
import com.cockpit.api.repository.UserStoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
public class BurnUpServiceTest {

    private BurnUpChartService burnUpChartService;

    @MockBean
    private JiraService jiraService;

    @MockBean
    private UserStoryService userStoryService;

    @MockBean
    private SprintService sprintService;

    @MockBean
    private MvpService mvpService;

    private ModelMapper modelMapper = new ModelMapper();

    @MockBean
    UserStoryRepository userStoryRepository;

    @Before
    public void setUp() {
        this.burnUpChartService = new BurnUpChartService(
                jiraService,
                userStoryService,
                sprintService,
                mvpService);
    }

    @Test
    public void whenGetChartDataThenReturnChartData() throws ResourceNotFoundException {

        MvpDTO mockMvpDTO = new MvpDTO();
        mockMvpDTO.setId(1l);
        Mvp mockMvp = modelMapper.map(mockMvpDTO, Mvp.class);
        Jira mockJira = new Jira();
        mockJira.setId(1l);
        mockJira.setMvp(mockMvp);
        Sprint mockSprint = new Sprint();
        mockSprint.setSprintNumber(1);
        mockSprint.setPuntedUsNumber(1);
        mockSprint.setCompletedUsNumber(2);
        mockSprint.setNotCompletedUsNumber(1);

        // given
        Mockito.when(mvpService.findMvpById(Mockito.anyLong())).thenReturn(mockMvpDTO);
        Mockito.when(jiraService.findByMvp(Mockito.any())).thenReturn(mockJira);
        Mockito.when(sprintService.findByJiraAndSprintNumber(Mockito.any(), Mockito.anyInt())).thenReturn(mockSprint);
        Mockito.when(userStoryService.getNumberOfStoriesInOneSprint(Mockito.any(), Mockito.any())).thenReturn(4);
        Mockito.when(sprintService.findSprintNumberForADate(Mockito.any(), Mockito.any())).thenReturn(1);


        // when
        List<BurnUpChartDTO> burnUpCharList = burnUpChartService.getChartData(1l);

        // then
        Assert.assertEquals(8, burnUpCharList.size());
        Assert.assertEquals(2, (long) burnUpCharList.get(0).getUsClosed());
        Assert.assertEquals(4, (long) burnUpCharList.get(0).getExpectedUsClosed());
    }

}
