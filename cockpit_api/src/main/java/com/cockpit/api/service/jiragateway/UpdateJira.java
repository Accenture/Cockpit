package com.cockpit.api.service.jiragateway;

import static javax.management.timer.Timer.ONE_HOUR;
import static javax.management.timer.Timer.ONE_SECOND;

import java.util.*;

import com.cockpit.api.exception.HttpException;
import com.cockpit.api.model.dto.jira.*;
import com.cockpit.api.service.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.service.UserStoryService;

@Configuration
@EnableScheduling
@Service
@Transactional
public class UpdateJira {
    Logger log = LoggerFactory.getLogger(UpdateJira.class);

    private final JiraRepository jiraRepository;
    private final HttpService httpService;
    final UserStoryService userStoryService;

    @Autowired
    public UpdateJira(JiraRepository jiraRepository, HttpService httpService,
                      UserStoryService userStoryService) {
        this.jiraRepository = jiraRepository;
        this.httpService = httpService;
        this.userStoryService = userStoryService;
    }

    @Value("${spring.jira.urlProjects}")
    private String urlProjects;
    @Value("${spring.jira.urlBoards}")
    private String urlBoards;

    @Scheduled(initialDelay = 5 * ONE_SECOND, fixedDelay = ONE_HOUR)
    public void updateProjectIdInJira() throws HttpException {
        log.info("Jira - Start update jira project id");
        ResponseEntity<Project[]> response = httpService.httpCall(urlProjects, Project[].class.getName());
        List<Project> jiraProjectsList = Arrays.asList(response.getBody());
        List<Jira> jiraList = jiraRepository.findAllByOrderById();
        for (Jira jira : jiraList) {
            if (jiraProjectsList.stream().anyMatch(projet -> projet.getKey().equals(jira.getJiraProjectKey()))) {
                Optional<Project> jiraProjectToUpdate = jiraProjectsList.stream()
                        .filter(projet -> projet.getKey().equals(jira.getJiraProjectKey())).findFirst();
                if (jiraProjectToUpdate.isPresent()) {
                    jira.setJiraProjectId(Integer.parseInt(jiraProjectToUpdate.get().getId()));
                    jiraRepository.save(jira);
                }
            }
        }
        log.info("Jira - End update jira project id");
    }

    @Scheduled(initialDelay = 5 * ONE_SECOND, fixedDelay = ONE_HOUR)
    public void updateBoardIdInJira() {
        log.info("Jira - Start update jira board id");
        try {
            updateBoardIdInJira(urlBoards);
        } catch (Exception e) {
            log.error("Failed to update board id of jira: {}", e.getMessage());
        }
        log.info("Jira - End update jira board id");
    }

    public void updateBoardIdInJira(String urlBoards) throws HttpException {
        List<JiraBoard> foundJiraBoards = new ArrayList<>();
        List<JiraBoard> boardList;

        int maxResults = 50;
        int startAt = 0;
        int numberOfBoardIdReceived;
        int totalBoardId;

        do {
            String url = String.format(urlBoards, maxResults, startAt);
            ResponseEntity<Board> result = httpService.httpCall(url, Board.class.getName());

            if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
                maxResults = result.getBody().getMaxResults();
                startAt = result.getBody().getStartAt();
                totalBoardId = result.getBody().getTotal();
                numberOfBoardIdReceived = startAt + maxResults;

                boardList = result.getBody().getValues();
                updateBoardId(boardList);
            } else {
                throw new HttpException("Failed to update board id for the jira");
            }
            startAt = startAt + maxResults;
            foundJiraBoards.addAll(boardList);
        } while (numberOfBoardIdReceived < totalBoardId);
    }

    private void updateBoardId(List<JiraBoard> boardList) throws HttpException {
        for (JiraBoard board : boardList) {
            try {
                if (board.getLocation() != null && board.getLocation().getProjectId() != null) {
                    Jira foundJira = jiraRepository.findByJiraProjectId((board.getLocation().getProjectId()));
                    if (foundJira != null && foundJira.getBoardId() != null) {
                        foundJira.setBoardId(board.getId());
                        jiraRepository.save(foundJira);
                    }
                }
            } catch (Exception e) {
                String message = "Jira - Unable to find Jira: " + board.getLocation().getProjectId();
                throw new HttpException(message);
            }
        }
    }
}
