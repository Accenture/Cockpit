package com.cockpit.api.service;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.repository.UserStoryRepository;
import com.cockpit.api.model.dao.Sprint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserStoryService {
    private final UserStoryRepository userStoryRepository;

    @Autowired
    public UserStoryService(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    public int getNumberOfStoriesInOneSprint(Sprint sprint, Jira jira) {
        int totalStories = 0;
        if (jira != null && sprint != null) {
            List<UserStory> userStoriesList = userStoryRepository.findUserStoriesByJiraAndSprintNumber(jira, sprint.getSprintNumber());

            if(userStoriesList != null && !userStoriesList.isEmpty())
            {
                totalStories = userStoriesList.size();
            }
        }
        return totalStories;
    }
}
