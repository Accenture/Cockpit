package cockpit.jiragateway.configurations;

import cockpit.jiragateway.exceptions.JiraException;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class UrlConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlConfiguration.class);

    HttpEntity<String> request;
    HttpHeaders headers;

    RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.application.jira.credentials}")
    private String jiraCredentials;

    public HttpHeaders addAuthorizationToHeaders() {
        byte[] plainCredsBytes = jiraCredentials.getBytes();
        byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        return headers;

    }

    public ResponseEntity<?> callJira(String url, String className) throws JiraException {


        headers = this.addAuthorizationToHeaders();
        request = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, Class.forName(className));
        } catch (ClassNotFoundException e) {
            LOGGER.error("JIRA did not respond with correct JSON");
            throw new JiraException("JIRA - Unable to convert JSON to POJO", e);
        } catch (RestClientException e) {
            LOGGER.error("Error occured while calling Jira");
            throw new JiraException("JIRA - Unable to call Jira to get data for " + url + " and class" + className, e);
        }
        return responseEntity;
    }
}
