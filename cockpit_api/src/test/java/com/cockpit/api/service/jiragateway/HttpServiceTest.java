package com.cockpit.api.service.jiragateway;

import com.cockpit.api.exception.HttpException;
import com.cockpit.api.model.dto.jira.Project;
import com.cockpit.api.service.HttpService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HttpService.class)
public class HttpServiceTest {

    @Autowired
    private HttpService httpService;

    @Value("${spring.jira.username}")
    private String username;
    @Value("${spring.jira.token}")
    private String token;
    @Value("${spring.jira.jiraUrl}")
    private String jiraUrl;
    @Value("${spring.jira.urlProjects}")
    private String urlProjects;
    @Value("${spring.sonarqube.sonarUrl}")
    private String sonarUrl;
    @Value("${spring.sonarqube.sonarUserName}")
    private String sonarUserName;
    @Before
    public void setUp() {
        this.httpService = new HttpService();
        ReflectionTestUtils.setField(httpService, "jiraUsername", username);
        ReflectionTestUtils.setField(httpService, "jiraToken", token);
        ReflectionTestUtils.setField(httpService, "jiraUrl", jiraUrl);
        ReflectionTestUtils.setField(httpService, "sonarUrl", sonarUrl);
        ReflectionTestUtils.setField(httpService, "sonarUserName", sonarUserName);
    }

    @Test
    public void whenCallingJiraUrlThenReturn200() throws HttpException {
        ResponseEntity response = httpService.httpCall(urlProjects, Project[].class.getName());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

}
