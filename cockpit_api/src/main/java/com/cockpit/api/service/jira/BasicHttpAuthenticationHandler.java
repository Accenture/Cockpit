package com.cockpit.api.service.jira;

import com.atlassian.httpclient.api.Request;
import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.util.Base64;
import org.springframework.beans.factory.annotation.Value;

public class BasicHttpAuthenticationHandler implements AuthenticationHandler {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static Logger logger = LogManager.getLogger(BasicHttpAuthenticationHandler.class);

    @Value("${jira.username}")
    private final String username;
    @Value("${jira.password}")
    private final String password;

    public BasicHttpAuthenticationHandler(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void configure(Request.Builder builder){
        builder.setHeader(AUTHORIZATION_HEADER, "Basic " + encodeCredentials());
    }

    private String encodeCredentials(){
        byte[] credentials = (username + ":" + password).getBytes();
        return new String(Base64.encodeBase64(credentials));
    }

}
