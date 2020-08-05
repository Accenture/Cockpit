package com.cockpit.api.service;

import com.cockpit.api.model.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Value("${spring.digitalpass.isEnabled}")
    private String isAuthEnabled;
    @Value("${spring.digitalpass.userInfoEndpoint}")
    private String userInfoEndpoint;

    public boolean isUserAuthorized(String authHeader) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = addAuthorizationToHeaders(authHeader);
        HttpEntity<String> request = new HttpEntity<>(headers);
        if (isAuthEnabled.equals("false")) {
            return true;
        } else {
            try {
                ResponseEntity responseEntity = restTemplate.exchange(userInfoEndpoint, HttpMethod.POST, request, UserInfoDTO.class);
                return responseEntity.getStatusCode().is2xxSuccessful();
            } catch (HttpClientErrorException e) {
                return false;
            }
        }
    }

    public HttpHeaders addAuthorizationToHeaders(String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);
        return headers;
    }

    public boolean isScrumMaster(String authHeader) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = addAuthorizationToHeaders(authHeader);
        HttpEntity<String> request = new HttpEntity<>(headers);
        if (isAuthEnabled.equals("false")) {
            return true;
        } else {
            try {
                ResponseEntity<UserInfoDTO> responseEntity = restTemplate.exchange(userInfoEndpoint, HttpMethod.POST, request, UserInfoDTO.class);
                return responseEntity.getBody().getGroups().contains("TDF_SCRUM_MASTERS");
            } catch (HttpClientErrorException e) {
                return false;
            }
        }
    }

}
