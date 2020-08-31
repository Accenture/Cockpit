package com.cockpit.api.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cockpit.api.service.AuthService;
import com.cockpit.api.service.jiragateway.JiraApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.cockpit.api.model.dto.JiraDTO;
import com.cockpit.api.service.JiraService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JiraController.class})
@WebMvcTest
public class JiraControllerTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JiraService jiraService;

    @MockBean
    private JiraApiService jiraApiService;

    @MockBean
    private AuthService authService;

    @Test
    public void whenCreateJiraThenReturn200() throws Exception {

        Jira mockJira = new Jira();

        JiraDTO jiraDto = modelMapper.map(mockJira, JiraDTO.class);

        // given
        Mockito.when(jiraService.createNewJiraProject(jiraDto)).thenReturn(jiraDto);
        Mockito.when(authService.isUserAuthorized(Mockito.any())).thenReturn(true);

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/jira/create")
                        .header("Authorization", "Bearer token")
                        .content(new ObjectMapper().writeValueAsString(jiraDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void whenGetJiraByIdThenReturn200() throws Exception {

        Jira mockJira = new Jira();
        mockJira.setId(1L);

        JiraDTO jiraDto = modelMapper.map(mockJira, JiraDTO.class);

        // given
        Mockito.when(jiraService.findJiraById(Mockito.anyLong())).thenReturn(jiraDto);
        Mockito.when(authService.isUserAuthorized(Mockito.any())).thenReturn(true);

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/jira/1")
                        .header("Authorization", "Bearer token")
                        .content(new ObjectMapper().writeValueAsString(jiraDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void whenUpdateJiraThenReturn200() throws Exception {

        Jira mockJira = new Jira();
        mockJira.setId(1L);

        JiraDTO jiraDto = modelMapper.map(mockJira, JiraDTO.class);

        // given
        Mockito.when(jiraService.updateJira(Mockito.any())).thenReturn(jiraDto);
        Mockito.when(authService.isUserAuthorized(Mockito.any())).thenReturn(true);

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/jira/update")
                        .header("Authorization", "Bearer token")
                        .content(new ObjectMapper().writeValueAsString(jiraDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void whenDeleteJiraThenReturn200() throws Exception {

        Jira mockJira = new Jira();
        mockJira.setId(1L);

        JiraDTO jiraDto = modelMapper.map(mockJira, JiraDTO.class);

        // given
        Mockito.when(jiraService.deleteJira(Mockito.any())).thenReturn(jiraDto);
        Mockito.when(authService.isUserAuthorized(Mockito.any())).thenReturn(true);

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/v1/jira/delete/1")
                        .header("Authorization", "Bearer token")
                        .content(new ObjectMapper().writeValueAsString(jiraDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }
}
