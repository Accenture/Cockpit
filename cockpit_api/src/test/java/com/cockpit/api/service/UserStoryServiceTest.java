package com.cockpit.api.service;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.repository.UserStoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class UserStoryServiceTest {

    private UserStoryService userStoryService;

    @MockBean
    UserStoryRepository userStoryRepository;

    @Before
    public void setUp() {
        this.userStoryService = new UserStoryService(userStoryRepository);
    }

    @Test
    public void whenGetNumberOfStoriesInOneSprintThenReturnInt() {
        Jira mockJira = new Jira();
        Sprint mockSprint = new Sprint();
        UserStory mockUserStory = new UserStory();
        List<UserStory> userStoryList = new ArrayList<>();
        userStoryList.add(mockUserStory);

        // given
        Mockito.when(userStoryRepository.findUserStoriesByJiraAndSprintNumber(
                Mockito.any(Jira.class), Mockito.anyInt())).thenReturn(userStoryList);

        // when
        int nbUserStories = userStoryService.getNumberOfStoriesInOneSprint(mockSprint, mockJira);

        // then
        Assert.assertEquals(1, nbUserStories);
    }

}
