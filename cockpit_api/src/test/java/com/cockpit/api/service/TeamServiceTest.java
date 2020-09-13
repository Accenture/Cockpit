package com.cockpit.api.service;

import java.util.*;

import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dto.TeamDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
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

	private ModelMapper modelMapper = new ModelMapper();

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
	public void whenCreateTeamThenReturnCreatedTeam() throws ResourceNotFoundException {

		Team mockTeam = new Team();
		TeamDTO teamDto = modelMapper.map(mockTeam, TeamDTO.class);

		Mvp mockMvp = new Mvp();
		mockMvp.setId(1l);
		mockMvp.setName("cockpit");

		// given
		Mockito.when(teamRepository.save(Mockito.any(Team.class))).thenReturn(mockTeam);
		Mockito.when(mvpRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockMvp));

		// when
		TeamDTO createdTeamDto = teamService.createNewTeam(teamDto, 1l);

		// then
		Assert.assertNotNull(createdTeamDto);

	}

	@Test
	public void whenGetTeamByIdThenReturnTeam() throws ResourceNotFoundException {
		Team mockTeam = new Team();
		mockTeam.setId(1l);
		mockTeam.setName("TEST");

		// given
		Mockito.when(teamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockTeam));

		// when
		TeamDTO teamDTO = teamService.findTeamById(1l);

		// then
		Assert.assertEquals("TEST", teamDTO.getName());
	}

	@Test
	public void whenFindAllTeamsThenReturnTeamList() throws ResourceNotFoundException {
		Team mockTeam = new Team();
		mockTeam.setId(1l);
		mockTeam.setName("TEST");
		List<Team> mockTeamList = new ArrayList<>();
		mockTeamList.add(mockTeam);

		// given
		Mockito.when(teamRepository.findAllByOrderByName()).thenReturn(mockTeamList);

		// when
		List<TeamDTO> teamDTOList = teamService.findAll();

		// then
		Assert.assertEquals(1, teamDTOList.size());
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

    @Test
    public void whenUnassignTeamMemberThenReturnUpdatedTeam() throws ResourceNotFoundException {

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
        teamService.unassignTeamMember(mockTeam.getId(), mockTeamMember.getId());

        // then
        Assert.assertTrue(mockTeam.getTeamMembers().isEmpty());

    }

    @Test
    public void whenAssignTeamMemberThenReturnUpdatedTeam() throws ResourceNotFoundException {

        Team mockTeam = new Team();
        mockTeam.setId(1l);
        mockTeam.setTeamMembers(new HashSet<>());
        TeamMember mockTeamMember = new TeamMember();
        mockTeamMember.setId(1l);
        mockTeamMember.setTeams(new HashSet<>());

        Optional<Team> team = Optional.ofNullable(mockTeam);
        Optional<TeamMember> teamMember = Optional.ofNullable(mockTeamMember);

        // given
        Mockito.when(teamRepository.findById(mockTeam.getId())).thenReturn(team);
        Mockito.when(teamMemberRepository.findById(mockTeamMember.getId())).thenReturn(teamMember);
        Mockito.when(teamRepository.save(mockTeam)).thenReturn(mockTeam);

        // when
        teamService.assignTeamMember(mockTeam.getId(), mockTeamMember.getId());

        // then
        Assert.assertFalse(mockTeam.getTeamMembers().isEmpty());

    }

    @Test
    public void whenGetAllMembersThenReturnMemberList() {
        TeamMember mockTeamMember = new TeamMember();
        mockTeamMember.setId(1l);
        mockTeamMember.setEmail("rihab@gmail.com");
        mockTeamMember.setFirstName("Rihab");
        mockTeamMember.setLastName("Rjab");
        mockTeamMember.setRole("PO");

        Team mockTeam = new Team();
        mockTeam.setId(1l);
        Set<Team> mockTeamList = new HashSet<>();
        mockTeamList.add(mockTeam);
        mockTeamMember.setTeams(mockTeamList);

        List<TeamMember> mockTeamMemberList = new ArrayList<>();
        mockTeamMemberList.add(mockTeamMember);

        // given
        Mockito.when(teamMemberRepository.findAllByOrderById()).thenReturn(mockTeamMemberList);

        // when
        List<TeamMemberDTO> teamMemberList = teamService.findAllMembers();

        // then
        Assert.assertEquals(1, teamMemberList.size());
    }

    @Test
    public void whenDeleteTeamThenReturnDeleteTeamFromDB() throws ResourceNotFoundException {
        Team mockTeam = new Team();
        mockTeam.setId(1l);
        Set<Mvp> mvpList = new HashSet<>();
        Mvp mockMvp = new Mvp();
        mockMvp.setId(1l);
        mockMvp.setName("cockpit");
        mockMvp.setEntity("RC");
        mockMvp.setCycle(1);
        mvpList.add(mockMvp);
        mockTeam.setMvps(mvpList);
        Optional<Team> team = Optional.ofNullable(mockTeam);

        // given
        Mockito.when(teamRepository.findById(mockTeam.getId())).thenReturn(team);

        // when
        teamService.deleteTeam(mockTeam.getId());

        // then
        Assert.assertNull(mockMvp.getTeam());
    }
}
