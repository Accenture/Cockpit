package com.cockpit.api.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cockpit.api.service.AuthService;
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
import com.cockpit.api.model.dao.TeamMember;
import com.cockpit.api.model.dto.TeamDTO;
import com.cockpit.api.model.dto.TeamMemberDTO;
import com.cockpit.api.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TeamController.class})
@WebMvcTest
public class TeamControllerTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @MockBean
    private AuthService authService;

    @Test
    public void whenAddTeamMemberThenReturn200() throws Exception {

        TeamMemberDTO mockTeamMember = new TeamMemberDTO();

        Team mockTeam = new Team();
        mockTeam.setId(1l);
        TeamDTO teamDto = modelMapper.map(mockTeam, TeamDTO.class);

		// given
		Mockito.when(teamService.createTeamMember(mockTeam.getId(), mockTeamMember)).thenReturn(teamDto);
		Mockito.when(authService.isUserAuthorized(Mockito.any())).thenReturn(true);

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/team/addTeamMember/{id}", mockTeam.getId())
                        .content(new ObjectMapper().writeValueAsString(mockTeamMember))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void whenDeleteTeamMemberThenReturn200() throws Exception {

        Team mockTeam = new Team();
        mockTeam.setId(1l);

        TeamMember mockTeamMember = new TeamMember();
        mockTeamMember.setId(1l);

        TeamDTO teamDto = modelMapper.map(mockTeam, TeamDTO.class);

        // given
        Mockito.when(teamService.deleteTeamMember(mockTeam.getId(), mockTeamMember.getId())).thenReturn(teamDto);

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/team/{id}/deleteTeamMember/{teamMeberId}", mockTeam.getId(), mockTeamMember.getId())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer token")).andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void whenUnassignTeamMemberThenReturn200() throws Exception {

        Team mockTeam = new Team();
        mockTeam.setId(1l);

        TeamMember mockTeamMember = new TeamMember();
        mockTeamMember.setId(1l);

        TeamDTO teamDto = modelMapper.map(mockTeam, TeamDTO.class);

        // given
        Mockito.when(teamService.unassignTeamMember(mockTeam.getId(), mockTeamMember.getId())).thenReturn(teamDto);

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/team/{id}/unassignTeamMember/{teamMeberId}", mockTeam.getId(), mockTeamMember.getId())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer token")).andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void whenAssignTeamMemberThenReturn200() throws Exception {

        Team mockTeam = new Team();
        mockTeam.setId(1l);

        TeamMember mockTeamMember = new TeamMember();
        mockTeamMember.setId(1l);

        TeamDTO teamDto = modelMapper.map(mockTeam, TeamDTO.class);

        // given
        Mockito.when(teamService.assignTeamMember(mockTeam.getId(), mockTeamMember.getId())).thenReturn(teamDto);

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/team/{id}/assignTeamMember/{teamMeberId}", mockTeam.getId(), mockTeamMember.getId())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer token")).andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void whenGetAllMembersThenReturn200() throws Exception {
        TeamMemberDTO mockTeamMember = new TeamMemberDTO();
        mockTeamMember.setId(1l);
        mockTeamMember.setEmail("rihab@gmail.com");
        mockTeamMember.setFirstName("Rihab");
        mockTeamMember.setLastName("Rjab");
        mockTeamMember.setRole("PO");

        Team mockTeam = new Team();
        mockTeam.setId(1l);
        Set<Team> mockTeamList = new HashSet<>();
        mockTeamList.add(mockTeam);
        mockTeamMember.setTeams(mockTeamList);

        List<TeamMemberDTO> mockTeamMemberList = new ArrayList<>();
        mockTeamMemberList.add(mockTeamMember);

        // given
        Mockito.when(teamService.findAllMembers()).thenReturn(mockTeamMemberList);

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/teamMember/all").accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void whenDeleteTeamThenReturn200() throws Exception {

        Team mockTeam = new Team();
        mockTeam.setId(1l);

        // given
        Mockito.doNothing().when(teamService).deleteTeam(mockTeam.getId());

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/team/delete/{id}", mockTeam.getId())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer token")).andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }
}
