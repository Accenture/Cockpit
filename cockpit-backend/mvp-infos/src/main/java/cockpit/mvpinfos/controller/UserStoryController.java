package cockpit.mvpinfos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.UserStory;
import cockpit.mvpinfos.service.UserStoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/1.0/user-story")
@Api(value = "/api/1.0/user-story")
@CrossOrigin
@Slf4j
public class UserStoryController {

    private UserStoryService userStoryService;
    @Autowired
    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @ApiOperation(value = "Get all user stories")
    @RequestMapping(value = "/all", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserStory> getAllUserStories() {
        log.info("getAllUserStories - return all the user stories in the db");
        return userStoryService.findAllUserStories();
    }

    @ApiOperation(value = "Get all user stories for a mvp")
    @RequestMapping(value = "/{mvp}", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserStory> getUserStoriesByMvp(@PathVariable("mvp") Mvp mvp) {
        log.info("getUserStoriesByMvp - return user stories of mvp : " + mvp.getId());
        return userStoryService.getUserStoriesByMvp(mvp);
    }

    @ApiOperation(value = "Get all user stories for a mvp by sprint")
    @RequestMapping(value = "/{mvp}/{sprintNumber}", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserStory> getUserStoriesBySprint(@PathVariable("mvp") Mvp mvp,
                                                  @PathVariable("sprintNumber") int sprintNumber) {
        log.info("getUserStoriesBySprint - return user stories of the mvp : " + mvp.getId()
                + " and sprint number: " + sprintNumber);
        return userStoryService.getUserStoriesByMvpBySprintNumber(mvp, sprintNumber);
    }
}
