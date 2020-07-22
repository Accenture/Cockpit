package com.cockpit.api.service.jiragateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

@Configuration
@Service
public class JiraApiConfiguration {
    Logger log = LoggerFactory.getLogger(JiraApiConfiguration.class);
    HttpEntity<String> request;
    HttpHeaders headers;
    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    public JiraApiConfiguration() {
        // Empty Constructor
    }

    @Value("${spring.jira.username}")
    private String username;
    @Value("${spring.jira.token}")
    private String token;

    public ResponseEntity<?> callJira(String url, String className) throws Exception {

        headers = this.addAuthorizationToHeaders();
        request = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, Class.forName(className));
        } catch (Exception e) {
            throw new Exception("Exception Call jira for url" + url);
        }
        return responseEntity;
    }
    public HttpHeaders addAuthorizationToHeaders() {

        String jiraCredentials = username + ":" + token;
        byte[] plainCredsBytes = jiraCredentials.getBytes();
        byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        return headers;

    }

}
