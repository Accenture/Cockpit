package com.cockpit.api.service;

import com.cockpit.api.model.dao.Impediment;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.repository.ImpedimentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.ObeyaDTO;
import com.cockpit.api.repository.SprintRepository;

import java.util.Date;
import java.util.HashSet;

@RunWith(SpringRunner.class)
public class SprintServiceTest {

    private SprintService sprintService;
    @MockBean
    private SprintRepository sprintRepository;
    @MockBean
    private ImpedimentRepository impedimentRepository;

    @Before
    public void setUp() {
        this.sprintService = new SprintService(sprintRepository, impedimentRepository);
    }

    @Test
    public void whenAddTeamHealthToSprintThenReturnSprint() {

        ObeyaDTO obeya = new ObeyaDTO(2, 2, 2);
        Sprint sprint = new Sprint();
        sprint.setId(1L);
        // Given
        Mockito.when(sprintRepository.save(sprint)).thenReturn(sprint);

        // When
        sprintService.setTeamHealth(obeya, sprint);

        // Then
        Assert.assertEquals(2, (int) (sprint.getTeamMood()));
        Assert.assertEquals(2, (int) (sprint.getTeamMotivation()));
        Assert.assertEquals(2, (int) (sprint.getTeamConfidence()));


    }

    @Test
    public void whenAddImpedimentToSprintThenReturnSprint() {
        Impediment impediment = new Impediment("impediment name", "impediment description");
        Sprint sprint = new Sprint();
        sprint.setId(1L);
        sprint.setImpediments(new HashSet<>());
        // Given
        Mockito.when(impedimentRepository.save(impediment)).thenReturn(impediment);

        // When
        sprintService.addImpediment(impediment, sprint);

        // Then
        Assert.assertEquals(impediment.getSprint(),sprint);
    }

    @Test
    public void whenFindSprintNumberForADateThenReturnSprintNumber() {
        Sprint mockSprint = new Sprint();
        mockSprint.setId(1L);
        mockSprint.setSprintNumber(10);
        Jira mockJira = new Jira();
        Date mockDate = new Date();
        // Given
        Mockito.when(sprintRepository.findTopBySprintStartDateLessThanEqualAndJiraEqualsOrderBySprintNumberDesc(Mockito.any(), Mockito.any())).thenReturn(mockSprint);

        // When
        int sprintNumber = sprintService.findSprintNumberForADate(mockJira, mockDate);

        // Then
        Assert.assertEquals(10, sprintNumber);
    }

    @Test
    public void whenFindByJiraAndSprintNumberThenReturnSprint() {
        Sprint mockSprint = new Sprint();
        mockSprint.setId(1L);
        mockSprint.setSprintNumber(10);
        Jira mockJira = new Jira();
        mockJira.setId(1l);

        // Given
        Mockito.when(sprintRepository.findByJiraAndSprintNumber(Mockito.any(), Mockito.anyInt())).thenReturn(mockSprint);

        // When
        Sprint foundSprint = sprintService.findByJiraAndSprintNumber(mockJira, 10);

        // Then
        Assert.assertEquals(10, foundSprint.getSprintNumber());
    }
}
