package com.cockpit.api.service.jiragateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

@Service
public class JiraApiService {
    HttpEntity<String> request;
    HttpHeaders headers;
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public JiraApiService() {
        // Empty Constructor
    }

    @Value("${spring.jira.username}")
    private String username;
    @Value("${spring.jira.token}")
    private String token;

    public ResponseEntity<?> callJira(String url, String className) throws Exception {

        headers = this.addAuthorizationToHeaders();
        request = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity;

        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, Class.forName(className));
        } catch (Exception e) {
            throw new Exception("Exception Call jira for url" + url);
        }
        return responseEntity;
    }
    public HttpHeaders addAuthorizationToHeaders() {

        String jiraCredentials = username + ":" + token;
        byte[] plainCredentialBytes = jiraCredentials.getBytes();
        byte[] base64CredentialBytes = Base64.getEncoder().encode(plainCredentialBytes);
        String base64Credential = new String(base64CredentialBytes);
        headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credential);
        return headers;

    }

}
