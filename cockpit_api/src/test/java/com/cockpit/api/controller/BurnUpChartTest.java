package com.cockpit.api.controller;

import com.cockpit.api.model.dto.BurnUpChartDTO;
import com.cockpit.api.service.AuthService;
import com.cockpit.api.service.BurnUpChartService;
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
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BurnUpChartController.class})
@WebMvcTest
public class BurnUpChartTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BurnUpChartService burnUpChartService;

    @MockBean
    private AuthService authService;

    @Test
    public void whenGetBurnUpChartDataThenReturn200() throws Exception {

        BurnUpChartDTO burnUpChartDTO = new BurnUpChartDTO();
        List<BurnUpChartDTO> burnUpChartList = new ArrayList<>();
        burnUpChartList.add(burnUpChartDTO);

        // given
        Mockito.when(burnUpChartService.getChartData(Mockito.anyLong())).thenReturn(burnUpChartList);
        Mockito.when(authService.isUserAuthorized(Mockito.any())).thenReturn(true);

        // when
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/burnUpChart/1")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }
}
