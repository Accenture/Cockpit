package cockpit.mvpinfos.service;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.Sprint;

import cockpit.cockpitcore.domaine.db.UserStory;
import cockpit.cockpitcore.repository.SprintRepository;

import cockpit.cockpitcore.repository.UserStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStoryService {

    private UserStoryRepository userStoryRepository;
    @Autowired
    public UserStoryService(UserStoryRepository userStoryRepository, SprintRepository sprintRepository) {
        this.userStoryRepository = userStoryRepository;
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

}
