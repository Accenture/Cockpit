package cockpit.mvpinfos.service;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.Sprint;
import cockpit.cockpitcore.domaine.db.UserStoriesHistory;
import cockpit.cockpitcore.domaine.db.UserStory;
import cockpit.cockpitcore.repository.UserStoriesHistoryRepository;
import cockpit.cockpitcore.repository.UserStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class UserStoryService {

    private UserStoryRepository userStoryRepository;
    private UserStoriesHistoryRepository userStoriesHistoryRepository;
    @Autowired
    public UserStoryService(UserStoryRepository userStoryRepository, UserStoriesHistoryRepository userStoriesHistoryRepository) {
        this.userStoryRepository = userStoryRepository;
        this.userStoriesHistoryRepository = userStoriesHistoryRepository;
    }

    public List<UserStory> getUserStoriesByMvpBySprintNumber(Mvp mvp, int sprintNumber) {

        return userStoryRepository.findMyUserStories(mvp, sprintNumber);
    }

    public List<UserStory> findAllUserStories() {
        return userStoryRepository.findAll();
    }

    public List<UserStory> getUserStoriesByMvp(Mvp mvp) {
        return userStoryRepository.findAllByMvp(mvp);
    }

    public Long findSumOfUsClosedForSprint(Mvp mvp, int sprintNumber) {

        return userStoryRepository.countNumberOfClosedUsPerSprint(mvp, sprintNumber);
    }

    public int getMaxNumberOfStoriesForADateOfAnMvp(Sprint sprint, Mvp mvp) {
        int totalStories = 0;
        if (mvp != null && sprint != null) {
            List<UserStory> userStoriesList = userStoryRepository.findMyUserStories (mvp, sprint.getSprintNumber());

            if(userStoriesList != null && !userStoriesList.isEmpty())
            {
                totalStories = userStoriesList.size();
            }
        }
        return totalStories;
    }

    public int getTotalStoriesNumberOfAnMvp(Mvp mvp) {
        int totalNumber = 0;
        if (mvp != null) {
            List<UserStory> userStoriesList = userStoryRepository.findAllByMvp(mvp);

            if(userStoriesList != null && !userStoriesList.isEmpty())
            {
                totalNumber = userStoriesList.size();
            }
        }
        return totalNumber;
    }
}
