package cockpit.mvpinfos.service;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.UserStory;
import cockpit.cockpitcore.repository.UserStoriesHistoryRepository;
import cockpit.cockpitcore.repository.UserStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

}
