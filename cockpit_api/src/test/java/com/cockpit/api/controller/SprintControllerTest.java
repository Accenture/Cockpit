package com.cockpit.api.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cockpit.api.model.dao.Impediment;
import com.cockpit.api.model.dto.*;
import com.cockpit.api.service.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.service.JiraService;
import com.cockpit.api.service.SprintService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SprintController.class})
@WebMvcTest
public class SprintControllerTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SprintService sprintService;

    @MockBean
    private JiraService jiraService;

    @MockBean
    private AuthService authService;

    @Test
    public void whenUpdateTeamHealthThenReturn200() throws Exception {
        Jira jira = new Jira();
        jira.setId(1L);
        Sprint sprint = new Sprint();
        sprint.setSprintNumber(1);
        ObeyaDTO obeya = new ObeyaDTO(2, 2, 2);

        // Given
        Mockito.when(jiraService.findJiraById(jira.getId())).thenReturn(modelMapper.map(jira, JiraDTO.class));
        Mockito.when(sprintService.findByJiraAndSprintNumber(jira, sprint.getSprintNumber())).thenReturn(sprint);
        Mockito.when(sprintService.setTeamHealth(obeya, sprint)).thenReturn(modelMapper.map(sprint, SprintDTO.class));
        Mockito.when(authService.isUserAuthorized(Mockito.any())).thenReturn(true);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/sprint/{jiraId}/updateTeamHealth/{sprintNumber}", jira.getId(), sprint.getSprintNumber())
                .content(new ObjectMapper().writeValueAsString(obeya))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer token"))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());


    }
    @Test
    public void whenAddImpedimentThenReturn200() throws Exception {
        Jira jira = new Jira();
        jira.setId(1L);
        Sprint sprint = new Sprint();
        sprint.setSprintNumber(1);
        Impediment impediment = new Impediment("impediment name", "impediment description");
        ImpedimentDTO impedimentDTO = modelMapper.map(impediment, ImpedimentDTO.class);

        // Given
        Mockito.when(jiraService.findJiraById(jira.getId())).thenReturn(modelMapper.map(jira, JiraDTO.class));
        Mockito.when(sprintService.findByJiraAndSprintNumber(jira, sprint.getSprintNumber())).thenReturn(sprint);
        Mockito.when(sprintService.addImpediment(impediment, sprint)).thenReturn(modelMapper.map(sprint, SprintDTO.class));
        Mockito.when(authService.isUserAuthorized(Mockito.any())).thenReturn(true);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/sprint/{jiraId}/addImpediment/{sprintNumber}", jira.getId(), sprint.getSprintNumber())
                .content(new ObjectMapper().writeValueAsString(impedimentDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer token"))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void whenGetSprintBySprintNumberAndJiraThenReturn200() throws Exception {
        Sprint mockSprint = new Sprint();
        mockSprint.setId(1l);
        Jira mockJira = new Jira();
        JiraDTO jiraDto = modelMapper.map(mockJira, JiraDTO.class);

        // given
        Mockito.when(jiraService.findJiraById(Mockito.anyLong())).thenReturn(jiraDto);
        Mockito.when(sprintService.findByJiraAndSprintNumber(Mockito.any(), Mockito.anyInt()))
                .thenReturn(mockSprint);
        Mockito.when(authService.isUserAuthorized(Mockito.any())).thenReturn(true);

        // when
        MvcResult resultGetTeamById = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/sprint/1/1").accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk()).andReturn();
        // then
        assertEquals(HttpStatus.OK.value(), resultGetTeamById.getResponse().getStatus());
    }
}
