package com.cockpit.api.controller;

import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dao.Technology;
import com.cockpit.api.model.dto.TechnologyDTO;
import com.cockpit.api.service.AuthService;
import com.cockpit.api.service.TechnologyService;
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

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TechnologyController.class})
@WebMvcTest
public class TechnologyControllerTest {
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TechnologyService technologyService;

    @MockBean
    private AuthService authService;

    @Test
    public void whenCreateTechnologyThenReturn200() throws Exception {
        Mvp mockMvp = new Mvp();
        mockMvp.setId(1l);
        TechnologyDTO mockTechnology = new TechnologyDTO();
        mockTechnology.setName("JAVA");
        mockTechnology.setUrl("https://www.tc-web.it/wp-content/uploads/2019/12/java.jpg");

        // given
        Mockito.when(technologyService.createNewTechnology(mockTechnology, mockMvp.getId())).thenReturn(mockTechnology);
        Mockito.when(authService.isScrumMaster(Mockito.any())).thenReturn(true);

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/technology/{id}/create", mockMvp.getId())
                        .header("Authorization", "Bearer token")
                        .content(new ObjectMapper().writeValueAsString(mockTechnology))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void whenAssignTechnologyThenReturn200() throws Exception {
        Mvp mockMvp = new Mvp();
        mockMvp.setId(1l);
        Technology mockTechnology = new Technology();
        mockTechnology.setId(1L);
        mockTechnology.setName("JAVA");
        mockTechnology.setUrl("https://www.tc-web.it/wp-content/uploads/2019/12/java.jpg");

        // given
        Mockito.when(technologyService.assignTechnology(mockTechnology.getId(), mockMvp.getId())).thenReturn(modelMapper.map(mockTechnology, TechnologyDTO.class));
        Mockito.when(authService.isScrumMaster(Mockito.any())).thenReturn(true);

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/technology/{id}/assign/{technoId}", mockMvp.getId(), mockTechnology.getId())
                        .header("Authorization", "Bearer token")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void whenUnassignTechnologyThenReturn200() throws Exception {
        Mvp mockMvp = new Mvp();
        mockMvp.setId(1l);
        Technology mockTechnology = new Technology();
        mockTechnology.setId(1L);
        mockTechnology.setName("JAVA");
        mockTechnology.setUrl("https://www.tc-web.it/wp-content/uploads/2019/12/java.jpg");

        // given
        Mockito.when(technologyService.unassignTechnology(mockTechnology.getId(), mockMvp.getId())).thenReturn(modelMapper.map(mockTechnology, TechnologyDTO.class));
        Mockito.when(authService.isScrumMaster(Mockito.any())).thenReturn(true);

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/technology/{id}/unassign/{technoId}", mockMvp.getId(), mockTechnology.getId())
                        .header("Authorization", "Bearer token")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
