package com.cockpit.api.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthService.class)
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Value("${spring.digitalpass.userInfoEndpoint}")
    private String userInfoEndpoint;

    @Before
    public void setUp() {
        this.authService = new AuthService();
        ReflectionTestUtils.setField(authService, "userInfoEndpoint", userInfoEndpoint);
    }

    @Test
    public void whenAuthEnabledButNotAuthorizedThenReturnFalse() {
        ReflectionTestUtils.setField(authService, "isAuthEnabled", "true");
        Assert.assertFalse(authService.isUserAuthorized("header"));
        Assert.assertFalse(authService.isScrumMaster("header"));
    }

    @Test
    public void whenAuthDisabledThenReturnFalse() {
        ReflectionTestUtils.setField(authService, "isAuthEnabled", "false");
        Assert.assertTrue(authService.isUserAuthorized("test"));
        Assert.assertTrue(authService.isScrumMaster("test"));
    }

}
