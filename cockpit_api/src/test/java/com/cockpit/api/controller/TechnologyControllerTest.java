package com.cockpit.api.controller;

import com.cockpit.api.service.AuthService;
import com.cockpit.api.service.TechnologyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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


    }
}
