package com.cockpit.api.service.sonargateway;

import com.cockpit.api.exception.HttpException;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dto.sonarqube.MetricResponse;
import com.cockpit.api.model.dto.sonarqube.ProjectComponent;
import com.cockpit.api.model.dto.sonarqube.ProjectListResponse;
import com.cockpit.api.repository.JiraRepository;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.management.timer.Timer.ONE_HOUR;
import static javax.management.timer.Timer.ONE_SECOND;

@Configuration
@EnableScheduling
@Service
public class UpdateTechnicalDebt {

    Logger log = LoggerFactory.getLogger(UpdateTechnicalDebt.class);

    private final HttpService httpService;

    private final JiraRepository jiraRepository;

    @Value("${spring.sonarqube.urlListProjects}")
    private String urlListProjects;
    @Value("${spring.sonarqube.urlTechnicalDebt}")
    private String urlTechnicalDebt;

    @Autowired
    public UpdateTechnicalDebt(
            JiraRepository jiraRepository,
            HttpService httpService
    ) {
        this.jiraRepository = jiraRepository;
        this.httpService = httpService;
    }

    @Scheduled(initialDelay = 20 * ONE_SECOND, fixedDelay = ONE_HOUR)
    public void updateTechnicalDebtFromJira() {
        log.info("Sonar - Start update technical debt");
        try {
            ResponseEntity<ProjectListResponse> sonarProjectList = httpService.httpCall(urlListProjects, ProjectListResponse.class.getName());
            if (sonarProjectList.getBody() != null) {
                List<ProjectComponent> projects = sonarProjectList.getBody().getComponents();
                List<String> sonarProjectKeys = computeSonarProjectKeys(projects);
                Map<String, List<String>> projectKeysMap = constructMapBetweenInoxKeyAndProjects(sonarProjectKeys);
                updateTechnicalDebtForEachMvp(projectKeysMap);
            }
        } catch (HttpException e) {
            log.error(e.getMessage());
        }
        log.info("Sonar - end update technical debt");
    }

    private List<String> computeSonarProjectKeys(List<ProjectComponent> projects) {
        // By default, sonar projects keys follow the principe to start with "TDF-"
        // Temporarily we take this to filter unknown sonar projects
        return projects.stream().map(ProjectComponent::getKey)
                .collect(Collectors.toList())
                .stream().filter(key -> key.startsWith("TDF-")).sorted()
                .collect(Collectors.toList());
    }

    private Map<String, List<String>> constructMapBetweenInoxKeyAndProjects(List<String> sonarProjectKeys) {
        // Collect All inox project keys to one list
        List<String> inoxProjectKeys = new ArrayList<>();
        sonarProjectKeys.forEach(key -> {
            String inoxProjectKey = key.split("-")[1];
            if (!inoxProjectKeys.contains(inoxProjectKey)) {
                inoxProjectKeys.add(inoxProjectKey);
            }
        });
        // Create a hashmap between inox project keys and all sonar projects
        Map<String, List<String>> projectKeysMap = new HashMap<>();
        for (String inoxProjectKey: inoxProjectKeys) {
            List<String> sonarProjectsForOneMvp = new ArrayList<>();
            sonarProjectKeys.forEach(key -> {
                if (key.contains(inoxProjectKey)) {
                    sonarProjectsForOneMvp.add(key);
                }
            });
            projectKeysMap.put(inoxProjectKey, sonarProjectsForOneMvp);
        }
        return projectKeysMap;
    }

    // Temporarily we map sonar and jira keys manually, to be discussed for the next
    private Map<String, String> createMapBetweenSonarKeyAndJiraKey() {
        Map<String, String> sonarKeyToJiraKey = new HashMap<>();
        sonarKeyToJiraKey.put("AGPO", "MAP");
        sonarKeyToJiraKey.put("C2FE", "RCFEE");
        sonarKeyToJiraKey.put("DERO", "GDERO");
        sonarKeyToJiraKey.put("SCEF", "ESCE");
        sonarKeyToJiraKey.put("TRAC", "MTT");
        sonarKeyToJiraKey.put("YETI", "YETI");
        sonarKeyToJiraKey.put("RTWP", "IAPE");
        sonarKeyToJiraKey.put("COKP", "TC");
        return sonarKeyToJiraKey;
    }

    private void updateTechnicalDebtForEachMvp(Map<String, List<String>> projectKeysMap) throws HttpException {
        Map<String, String> mapSonarAndJira = createMapBetweenSonarKeyAndJiraKey();
        // Several sonar projects shares the same inox project key, need to make the sum for their debts
        for (Map.Entry<String, List<String >> keyMap : projectKeysMap.entrySet()) {
            if (!keyMap.getValue().isEmpty()) {
                int totalTechnicalDebtInMins = 0;
                for(String sonarProjectKey: keyMap.getValue()) {
                    String url = String.format(urlTechnicalDebt, sonarProjectKey);
                    ResponseEntity<MetricResponse> sonarMetric = httpService.httpCall(url, MetricResponse.class.getName());
                    if (sonarMetric.getBody() != null && sonarMetric.getBody().getComponent() != null) {
                        int technicalDebtInMins = Integer.parseInt(sonarMetric.getBody().getComponent().getMeasures().get(0).getValue());
                        totalTechnicalDebtInMins = totalTechnicalDebtInMins + technicalDebtInMins;
                    }
                }
                Jira foundJira = jiraRepository.findByJiraProjectKey(mapSonarAndJira.get(keyMap.getKey()));
                if (foundJira != null) {
                    foundJira.getMvp().setTechnicalDebt(totalTechnicalDebtInMins);
                    jiraRepository.save(foundJira);
                }
            }
        }
    }
}
