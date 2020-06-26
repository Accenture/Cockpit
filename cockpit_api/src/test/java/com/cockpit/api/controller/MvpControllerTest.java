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

import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dao.Team;
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

	@Test
	public void WhenUnassignTeamOfMvp_thenReturn200() throws Exception {
		Mvp mockMvp = new Mvp();
		Team mockTeam = new Team();
		mockTeam.setId(1l);
		mockMvp.setId(1l);
		mockTeam.setName("team1");
		mockMvp.setTeam(mockTeam);
		MvpDTO mvpDto = modelMapper.map(mockMvp, MvpDTO.class);

		// given
		Mockito.when(mvpService.unassignTeamOfMvp(mockMvp.getId())).thenReturn(mvpDto);
		
		// when
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/mvp/unassignTeam/{id}", mockMvp.getId())
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		// then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

}