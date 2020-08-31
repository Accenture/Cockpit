package com.cockpit.api.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.cockpit.api.model.dao.*;
import com.cockpit.api.model.dto.ImpedimentDTO;
import com.cockpit.api.model.dto.JiraDTO;
import com.cockpit.api.model.dto.SprintDTO;
import com.cockpit.api.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.cockpit.api.model.dto.MvpDTO;

import com.cockpit.api.service.MvpService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MvpController.class })
@WebMvcTest
public class MvpControllerTest {
	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MvpService mvpService;

	@MockBean
	private AuthService authService;

	@Test
	public void whenUnassignTeamOfMvpThenReturn200() throws Exception {
		Mvp mockMvp = new Mvp();
		Team mockTeam = new Team();
		mockTeam.setId(1l);
		mockMvp.setId(1l);
		mockTeam.setName("team1");
		mockMvp.setTeam(mockTeam);
		MvpDTO mvpDto = modelMapper.map(mockMvp, MvpDTO.class);

		// given
		Mockito.when(mvpService.unassignTeamOfMvp(mockMvp.getId())).thenReturn(mvpDto);
		Mockito.when(authService.isUserAuthorized(Mockito.any())).thenReturn(true);

		// when
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/mvp/unassignTeam/{id}", mockMvp.getId())
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer token")).andExpect(status().isOk()).andReturn();

		MockHttpServletResponse response = result.getResponse();

		// then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void whenAssignTeamToMvpThenReturn200() throws Exception {
		Mvp mockMvp = new Mvp();
		Team mockTeam = new Team();
		mockTeam.setId(1l);
		mockMvp.setId(1l);
		mockTeam.setName("team1");
		MvpDTO mvpDto = modelMapper.map(mockMvp, MvpDTO.class);

		// given
		Mockito.when(mvpService.assignTeamOfMvp(mockMvp.getId(),mockTeam.getId())).thenReturn(mvpDto);
		Mockito.when(authService.isUserAuthorized(Mockito.any())).thenReturn(true);

		// when
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/mvp/{id}/assignTeam/{teamId}", mockMvp.getId(),mockTeam.getId())
				.accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer token")).andExpect(status().isOk()).andReturn();

		MockHttpServletResponse response = result.getResponse();

		// then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void whenGetTeamByIdOrGetAllTeamsThenReturn200() throws Exception {
		MvpDTO mockMvp = new MvpDTO();
		mockMvp.setId(1l);
		List<MvpDTO> mockMvpList = new ArrayList<>();
		mockMvpList.add(mockMvp);
		
		// given
		Mockito.when(mvpService.findMvpById(Mockito.anyLong())).thenReturn(mockMvp);
		Mockito.when(mvpService.findAllMvp()).thenReturn(mockMvpList);
		Mockito.when(authService.isUserAuthorized(Mockito.any())).thenReturn(true);

		// when
		MvcResult resultGetMvpById = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/mvp/1").accept(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer token"))
				.andExpect(status().isOk()).andReturn();
		MvcResult resultGetAllMvps = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/mvp/all").accept(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer token"))
				.andExpect(status().isOk()).andReturn();
		// then
		assertEquals(HttpStatus.OK.value(), resultGetMvpById.getResponse().getStatus());
		assertEquals(HttpStatus.OK.value(), resultGetAllMvps.getResponse().getStatus());
	}

	@Test
	public void whenCreateMvpThenReturn200() throws Exception {
		MvpDTO mockMvp = new MvpDTO();
		mockMvp.setId(1l);

		// Given
		Mockito.when(mvpService.createNewMvp(mockMvp)).thenReturn(modelMapper.map(mockMvp, MvpDTO.class));
		Mockito.when(authService.isUserAuthorized(Mockito.any())).thenReturn(true);

		// When
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/mvp/create")
				.content(new ObjectMapper().writeValueAsString(mockMvp))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer token"))
				.andExpect(status().isOk()).andReturn();

		MockHttpServletResponse response = result.getResponse();

		// then
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

}
