package com.cockpit.api.service.jiragateway;

import static javax.management.timer.Timer.ONE_HOUR;
import static javax.management.timer.Timer.ONE_MINUTE;
import static javax.management.timer.Timer.ONE_SECOND;

import java.util.*;

import com.cockpit.api.model.dto.jira.*;
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
import com.cockpit.api.repository.SprintRepository;
import com.cockpit.api.repository.UserStoryRepository;
import com.cockpit.api.service.UserStoryService;

@Configuration
@EnableScheduling
@Service
@Transactional
public class UpdateJiraService {
    Logger log = LoggerFactory.getLogger(UpdateJiraService.class);

    private final JiraRepository jiraRepository;
    private final JiraApiConfiguration jiraApiConfiguration;
    final UserStoryService userStoryService;

    @Autowired
    public UpdateJiraService(JiraRepository jiraRepository, JiraApiConfiguration jiraApiConfiguration,
                             UserStoryService userStoryService) {
        this.jiraRepository = jiraRepository;
        this.jiraApiConfiguration = jiraApiConfiguration;
        this.userStoryService = userStoryService;
    }

    @Value("${spring.jira.urlProjects}")
    private String urlProjects;
    @Value("${spring.jira.urlBoards}")
    private String urlBoards;

    @Scheduled(initialDelay = 5 * ONE_SECOND, fixedDelay = 10 * ONE_MINUTE)
    public void updateProjectId() throws Exception {
        ResponseEntity<Project[]> response = (ResponseEntity<Project[]>) jiraApiConfiguration.callJira(urlProjects,
                Project[].class.getName());
        List<Project> jiraProjectsList = Arrays.asList(response.getBody());
        List<Jira> jiraList = jiraRepository.findAllByOrderById();
        for (Jira jira : jiraList) {
            if (jiraProjectsList.stream().filter(projet -> projet.getKey().equals(jira.getJiraProjectKey())).findFirst()
                    .isPresent()) {
                Project jProjet = jiraProjectsList.stream()
                        .filter(projet -> projet.getKey().equals(jira.getJiraProjectKey())).findFirst().get();
                jira.setJiraProjectId(Integer.parseInt(jProjet.getId()));
                jiraRepository.save(jira);
            }

        }

    }

    @Scheduled(initialDelay = 5 * ONE_SECOND, fixedDelay = 2 * ONE_HOUR)
    public void updateBoardIdInJira() {
        try {
            updateBoardIdInJira(urlBoards);
        } catch (Exception e) {
        }
    }

    public void updateBoardIdInJira(String urlBoards) throws Exception {
        List<JiraBoard> foundJiraBoards = new ArrayList<>();
        List<JiraBoard> boardList = null;

        int maxResults = 50;
        int startAt = 0;
        int numberOfBoardIdReceived = 0;
        int totalBoardId;

        do {
            String url = String.format(urlBoards, maxResults, startAt);
            ResponseEntity<Board> result = (ResponseEntity<Board>) jiraApiConfiguration.callJira(url, Board.class.getName());

            if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
                maxResults = result.getBody().getMaxResults();
                startAt = result.getBody().getStartAt();
                totalBoardId = result.getBody().getTotal();
                numberOfBoardIdReceived = startAt + maxResults;

                boardList = result.getBody().getValues();
                updateBoard(boardList);
            } else {
                throw new Exception("jira - Response code : while trying to update board for the jira ");
            }
            startAt = startAt + maxResults;
            foundJiraBoards.addAll(boardList);
        } while (numberOfBoardIdReceived < totalBoardId);
    }

    private void updateBoard(List<JiraBoard> boardList) throws Exception {
        for (JiraBoard board : boardList) {
            try {
                if (board.getLocation() != null && board.getLocation().getProjectId() != null) {
                    Optional<Jira> optional = jiraRepository.findByJiraProjectId((board.getLocation().getProjectId()));
                    if (optional.isPresent()) {
                        Jira jira = optional.get();
                        jira.setBoardId(board.getId());
                        jiraRepository.save(jira);
                    }
                }
            } catch (Exception e) {
                String message = "Jira - Unable to find Jira: " + board.getLocation().getProjectId();
                throw new Exception(message, e);
            }
        }
    }
}
