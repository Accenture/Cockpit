package com.cockpit.api.service.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;

public class JiraClient {

    @Value("${jira.username}")
    private final String username;
    @Value("${jira.password}")
    private final String password;
    @Value("${jira.jiraUrl}")
    private String jiraUrl;
    private JiraRestClient restClient;

    private JiraClient(String username, String password, String jiraUrl) {
        this.username = username;
        this.password = password;
        this.jiraUrl = jiraUrl;
        this.restClient = getJiraRestClient();
    }

    // READ
    @GetMapping(
            value = "/api/v1/jira/project/{jiraKey}"
    )
    public @ResponseBody
    ResponseEntity<JiraRestClient> getUSer(@PathVariable Long id) {
        return ResponseEntity.ok(getJiraRestClient());
    }

    private JiraRestClient getJiraRestClient() {
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(getJiraUri(), this.username, this.password);
    }

    private URI getJiraUri() {
        return URI.create(this.jiraUrl);
    }

}
