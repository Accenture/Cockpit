package com.cockpit.api.service;

import com.cockpit.api.exception.HttpException;
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
public class HttpService {
    @Autowired
    public HttpService() {
        // Empty Constructor
    }

    @Value("${spring.jira.username}")
    private String jiraUsername;
    @Value("${spring.jira.token}")
    private String jiraToken;
    @Value("${spring.jira.jiraUrl}")
    private String jiraUrl;
    @Value("${spring.sonarqube.sonarUrl}")
    private String sonarUrl;
    @Value("${spring.sonarqube.username}")
    private String sonarUserName;

    public ResponseEntity httpCall(String url, String className) throws HttpException {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity responseEntity = null;

        try {
            if (url.startsWith(jiraUrl)) {
                HttpHeaders headers = this.addAuthorizationToHeaders(jiraUsername, jiraToken);;
                HttpEntity<String> request = new HttpEntity<>(headers);
                responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, Class.forName(className));
            }
            if (url.startsWith(sonarUrl)) {
                HttpHeaders headers = this.addAuthorizationToHeaders(sonarUserName, "");;
                HttpEntity<String> request = new HttpEntity<>(headers);
                responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, Class.forName(className));
            }
        } catch (Exception e) {
            throw new HttpException("HTTP Exception for url" + url);
        }
        return responseEntity;
    }
    public HttpHeaders addAuthorizationToHeaders(String username, String password) {

        String jiraCredentials = username + ":" + password;
        byte[] plainCredentialBytes = jiraCredentials.getBytes();
        byte[] base64CredentialBytes = Base64.getEncoder().encode(plainCredentialBytes);
        String base64Credential = new String(base64CredentialBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credential);
        return headers;

    }

}
