package com.cockpit.api.controller;

import com.cockpit.api.model.dao.Mvp;
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
        TechnologyDTO technologyDTO = new TechnologyDTO();
        technologyDTO.setName("JAVA");
        technologyDTO.setUrl("https://www.tc-web.it/wp-content/uploads/2019/12/java.jpg");

        // given
        Mockito.when(technologyService.createNewTechnology(technologyDTO, mockMvp.getId())).thenReturn(technologyDTO);

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/technology/{id}/create", mockMvp.getId())
                        .header("Authorization", "Bearer token")
                        .content(new ObjectMapper().writeValueAsString(technologyDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
