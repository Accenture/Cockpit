package com.cockpit.api.service;

import com.cockpit.api.model.dao.Impediment;
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

}
