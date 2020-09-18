package com.cockpit.api.service.jiragateway;

import com.cockpit.api.exception.JiraException;
import com.cockpit.api.model.dto.jira.Project;
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
@SpringBootTest(classes = JiraApiService.class)
public class JiraApiServiceTest {

    @Autowired
    private JiraApiService jiraApiService;

    @Value("${spring.jira.username}")
    private String username;
    @Value("${spring.jira.token}")
    private String token;
    @Value("${spring.jira.jiraUrl}")
    private String jiraUrl;
    @Value("${spring.jira.urlProjects}")
    private String urlProjects;

    @Before
    public void setUp() {
        this.jiraApiService = new JiraApiService();
        ReflectionTestUtils.setField(jiraApiService, "username", username);
        ReflectionTestUtils.setField(jiraApiService, "token", token);
        ReflectionTestUtils.setField(jiraApiService, "jiraUrl", jiraUrl);
    }

    @Test
    public void whenCallingJiraUrlThenReturn200() throws JiraException {
        ResponseEntity response = jiraApiService.callJira(urlProjects, Project[].class.getName());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

}
