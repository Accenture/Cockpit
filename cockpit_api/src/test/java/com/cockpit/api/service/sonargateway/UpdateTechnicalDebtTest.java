package com.cockpit.api.service.sonargateway;

import com.cockpit.api.exception.HttpException;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dto.sonarqube.*;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.service.HttpService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HttpService.class)
public class UpdateTechnicalDebtTest {

    private UpdateTechnicalDebt updateTechnicalDebt;

    @MockBean
    private HttpService httpService;

    @MockBean
    private JiraRepository jiraRepository;

    @Value("${spring.sonarqube.urlListProjects}")
    private String urlListProjects;
    @Value("${spring.sonarqube.urlTechnicalDebt}")
    private String urlTechnicalDebt;

    @Before
    public void setUp() {
        this.updateTechnicalDebt = new UpdateTechnicalDebt(jiraRepository, httpService);
        ReflectionTestUtils.setField(updateTechnicalDebt, "urlListProjects", urlListProjects);
        ReflectionTestUtils.setField(updateTechnicalDebt, "urlTechnicalDebt", urlTechnicalDebt);
    }

    @Test
    public void whenUpdateTechnicalDebtThenJiraAndMvpUpdated() throws HttpException {

        Mvp mockMvp = new Mvp();
        mockMvp.setTechnicalDebt(100);
        Jira mockJira = new Jira();
        mockJira.setJiraProjectKey("TC");
        mockJira.setMvp(mockMvp);

        ProjectListResponse mockProjectListResponse = new ProjectListResponse();
        ProjectComponent mockProjectComponent = new ProjectComponent();
        mockProjectComponent.setKey("TDF-COKP-TEST");
        List<ProjectComponent> mockProjectCompList = new ArrayList<>();
        mockProjectCompList.add(mockProjectComponent);
        mockProjectListResponse.setComponents(mockProjectCompList);
        ResponseEntity mockProjectsResponse = new ResponseEntity(mockProjectListResponse, HttpStatus.OK);

        MetricResponse mockMetricResponse = new MetricResponse();
        MetricComponent mockMetricComponent = new MetricComponent();
        Measure mockMeasure = new Measure();
        mockMeasure.setValue("100");
        List<Measure> mockMeasureList = new ArrayList<>();
        mockMeasureList.add(mockMeasure);
        mockMetricComponent.setMeasures(mockMeasureList);
        mockMetricResponse.setComponent(mockMetricComponent);

        ResponseEntity mockTechnicalDebtResponse = new ResponseEntity(mockMetricResponse, HttpStatus.OK);


        // given
        Mockito.when(httpService.httpCall(urlListProjects, ProjectListResponse.class.getName())).thenReturn(mockProjectsResponse);
        Mockito.when(httpService.httpCall(String.format(urlTechnicalDebt, "TDF-COKP-TEST"), MetricResponse.class.getName())).thenReturn(mockTechnicalDebtResponse);
        Mockito.when(jiraRepository.findByJiraProjectKey(Mockito.anyString())).thenReturn(mockJira);

        // when
        updateTechnicalDebt.updateTechnicalDebtFromJira();

        // then
        verify(jiraRepository, atLeastOnce()).save(mockJira);
    }
}
