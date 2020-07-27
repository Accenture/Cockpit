package com.cockpit.api.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.JiraDTO;
import com.cockpit.api.model.dto.ObeyaDTO;
import com.cockpit.api.service.JiraService;
import com.cockpit.api.service.SprintService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SprintController.class })
@WebMvcTest
public class SprintControllerTest {

	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SprintService sprintService;

	@MockBean
	private JiraService jiraService;

	@Test
	public void whenUpdateTeamHealthThenReturn200() throws Exception
	{
		Jira jira = new Jira();
		jira.setId(1L);
		Sprint sprint = new Sprint();
		sprint.setSprintNumber(1);
		ObeyaDTO obeya = new ObeyaDTO(2,2,2);
		
		// Given
		Mockito.when(jiraService.findJiraById(jira.getId())).thenReturn(modelMapper.map(jira, JiraDTO.class));
		Mockito.when(sprintService.findByMvpAndSprintNumber(jira, sprint.getSprintNumber())).thenReturn(sprint);
		
		// When
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/sprint/{jiraId}/updateTeamHealth/{sprintNumber}", jira.getId(), sprint.getSprintNumber()	 )
				.content(new ObjectMapper().writeValueAsString(obeya))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		MockHttpServletResponse response = result.getResponse();

		// then
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		
	}
}