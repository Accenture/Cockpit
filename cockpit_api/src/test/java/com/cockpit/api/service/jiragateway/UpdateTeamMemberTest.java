package com.cockpit.api.service.jiragateway;

import com.cockpit.api.exception.JiraException;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dao.TeamMember;
import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.model.dto.jira.*;
import com.cockpit.api.repository.JiraRepository;
import com.cockpit.api.repository.SprintRepository;
import com.cockpit.api.repository.TeamMemberRepository;
import com.cockpit.api.repository.UserStoryRepository;
import com.cockpit.api.service.UserStoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UpdateTeamMember.class)
public class UpdateTeamMemberTest {

    private UpdateTeamMember updateTeamMember;

    @MockBean
    private JiraApiService jiraApiService;

    @MockBean
    private TeamMemberRepository teamMemberRepository;

    @Value("${spring.jira.urlUserInformation}")
    private String urlUserInformation;

    @Before
    public void setUp() {
        this.updateTeamMember = new UpdateTeamMember(
                teamMemberRepository,
                jiraApiService
        );
        ReflectionTestUtils.setField(updateTeamMember, "urlUserInformation", urlUserInformation); }

    @Test
    public void whenUpdateTeamMemberThenTeamMemberUpdated() throws JiraException {

        User mockUser = new User();
        AvatarUrls mockAvatarUrls = new AvatarUrls();
        mockAvatarUrls.setBigAvatar("https://test.com");
        mockUser.setAvatarUrls(mockAvatarUrls);
        User[] users = new User[1];
        users[0] = mockUser;
        ResponseEntity mockResponse = new ResponseEntity(users,HttpStatus.OK);

        TeamMember mockTeamMember = new TeamMember();
        mockTeamMember.setId(1l);
        mockTeamMember.setEmail("test@cockpit.com");
        List<TeamMember> mockTeamMemberList = new ArrayList<>();
        mockTeamMemberList.add(mockTeamMember);
        // given
        Mockito.when(jiraApiService.callJira(urlUserInformation + "test@cockpit.com", User[].class.getName())).thenReturn(mockResponse);
        Mockito.when(teamMemberRepository.findAllByOrderById()).thenReturn(mockTeamMemberList);

        // when
        updateTeamMember.updateTeamMembers();

        // then
        verify(teamMemberRepository, atLeastOnce()).save(Mockito.any(TeamMember.class));

    }
}
