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

import com.cockpit.api.model.dao.Team;
import com.cockpit.api.model.dto.TeamDTO;
import com.cockpit.api.model.dto.TeamMemberDTO;
import com.cockpit.api.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TeamController.class })
@WebMvcTest
public class TeamControllerTest {

	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TeamService teamService ;
	
	@Test
	public void whenAddTeamMemberThenReturn200() throws Exception
	{
		
		TeamMemberDTO mockTeamMember= new TeamMemberDTO();
		
		Team mockTeam= new Team();
		mockTeam.setId(1l);
		TeamDTO teamDto = modelMapper.map(mockTeam, TeamDTO.class);
		
		// given
		Mockito.when(teamService.createTeamMember(mockTeam.getId(),mockTeamMember)).thenReturn(teamDto);
	
		
		// when
				MvcResult result = mockMvc
						.perform(MockMvcRequestBuilders.put("/api/v1/team/addTeamMember/{id}", mockTeam.getId())
						.content(new ObjectMapper().writeValueAsString(mockTeamMember))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk()).andReturn();

				MockHttpServletResponse response = result.getResponse();

				// then
				assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
}
