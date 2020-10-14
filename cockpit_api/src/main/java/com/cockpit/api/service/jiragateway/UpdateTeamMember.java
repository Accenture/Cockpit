package com.cockpit.api.service.jiragateway;

import com.cockpit.api.exception.HttpException;
import com.cockpit.api.model.dao.TeamMember;
import com.cockpit.api.model.dto.jira.User;
import com.cockpit.api.repository.TeamMemberRepository;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.management.timer.Timer.ONE_HOUR;
import static javax.management.timer.Timer.ONE_SECOND;

@Configuration
@EnableScheduling
@Service
@Transactional
public class UpdateTeamMember {
    Logger log = LoggerFactory.getLogger(UpdateTeamMember.class);
    private final TeamMemberRepository teamMemberRepository;
    private final HttpService httpService;

    @Autowired
    public UpdateTeamMember(TeamMemberRepository teamMemberRepository, HttpService httpService) {
        this.teamMemberRepository = teamMemberRepository;
        this.httpService = httpService;
    }

    @Value("${spring.jira.urlUserInformation}")
    private String urlUserInformation;

    @Scheduled(initialDelay = 15 * ONE_SECOND, fixedDelay = ONE_HOUR)
    public void updateTeamMembers() throws HttpException {
        log.info("Team Member - Start update team members");
        List<TeamMember> teamMemberList = teamMemberRepository.findAllByOrderById();
        List<TeamMember> updatedTeamMembers = new ArrayList<>();
        for (TeamMember teamMember : teamMemberList) {
            String url = urlUserInformation + teamMember.getEmail();
            ResponseEntity<User[]> response = httpService.httpCall(url, User[].class.getName());
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<User> userList = Arrays.asList(response.getBody());
                if (!userList.isEmpty()) {
                    teamMember.setUrlTeamMemberAvatar(userList.get(0).getAvatarUrls().getBigAvatar());
                    updatedTeamMembers.add(teamMember);
                }
            }
        }
        teamMemberRepository.saveAll(updatedTeamMembers);
        log.info("Team Member - End update team members");

    }
}
