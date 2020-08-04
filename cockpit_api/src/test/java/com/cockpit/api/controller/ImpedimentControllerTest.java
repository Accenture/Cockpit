package com.cockpit.api.controller;

import com.cockpit.api.model.dao.Impediment;
import com.cockpit.api.service.AuthService;
import com.cockpit.api.service.ImpedimentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
@ContextConfiguration(classes = {ImpedimentController.class})
@WebMvcTest
public class ImpedimentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImpedimentService impedimentService;

    @MockBean
    private AuthService authService;

    @Test
    public void whenDeleteImpedimentThenReturn200() throws Exception {
        Impediment mockImpediment = new Impediment("impediment name", "impediment description");
        mockImpediment.setId(1L);

        // given
        Mockito.doNothing().when(impedimentService).deleteImpediment(mockImpediment.getId());

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/v1/impediment/delete/{id}", mockImpediment.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
