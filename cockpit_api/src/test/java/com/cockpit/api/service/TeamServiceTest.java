package com.cockpit.api.service;

import java.util.HashSet;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Team;
import com.cockpit.api.model.dao.TeamMember;
import com.cockpit.api.model.dto.TeamMemberDTO;
import com.cockpit.api.repository.MvpRepository;
import com.cockpit.api.repository.TeamMemberRepository;
import com.cockpit.api.repository.TeamRepository;

@RunWith(SpringRunner.class)
public class TeamServiceTest {

	private TeamService teamService;

	@MockBean
	TeamRepository teamRepository;

	@MockBean
	MvpRepository mvpRepository;

	@MockBean
	TeamMemberRepository teamMemberRepository;

	@Before
	public void setUp() {
		this.teamService = new TeamService(teamRepository, mvpRepository, teamMemberRepository);
	}

	@Test
	public void whenAddTeamMemberThenReturnUpdatedTeam() throws ResourceNotFoundException {
		TeamMemberDTO mockTeamMember = new TeamMemberDTO();

		Team mockTeam = new Team();
		mockTeam.setId(1l);
		mockTeam.setTeamMembers(new HashSet<>());
		Optional<Team> team = Optional.ofNullable(mockTeam);

		// given
		Mockito.when(teamRepository.findById(mockTeam.getId())).thenReturn(team);
		Mockito.when(teamRepository.save(mockTeam)).thenReturn(mockTeam);

		// when
		teamService.createTeamMember(mockTeam.getId(), mockTeamMember);

		// then
		Assert.assertFalse(mockTeam.getTeamMembers().isEmpty());

	}

	@Test
	public void whenDeleteTeamMemberThenReturnUpdatedTeam() throws ResourceNotFoundException {

		Team mockTeam = new Team();
		mockTeam.setId(1l);
		mockTeam.setTeamMembers(new HashSet<>());
		TeamMember mockTeamMember = new TeamMember();
		mockTeamMember.setId(1l);
		mockTeamMember.setTeams(new HashSet<>());
		mockTeamMember.getTeams().add(mockTeam);
		
		mockTeam.getTeamMembers().add(mockTeamMember);
		Optional<Team> team = Optional.ofNullable(mockTeam);
		Optional<TeamMember> teamMember = Optional.ofNullable(mockTeamMember);

		// given
		Mockito.when(teamRepository.findById(mockTeam.getId())).thenReturn(team);
		Mockito.when(teamMemberRepository.findById(mockTeamMember.getId())).thenReturn(teamMember);
		Mockito.when(teamRepository.save(mockTeam)).thenReturn(mockTeam);

		// when
		teamService.deleteTeamMember(mockTeam.getId(), mockTeamMember.getId());

		// then
		Assert.assertTrue(mockTeam.getTeamMembers().isEmpty());
	
	}
}