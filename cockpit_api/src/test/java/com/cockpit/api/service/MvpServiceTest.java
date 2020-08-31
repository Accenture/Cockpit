package com.cockpit.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dao.Team;
import com.cockpit.api.model.dto.MvpDTO;
import com.cockpit.api.repository.MvpRepository;
import com.cockpit.api.repository.TeamRepository;

@RunWith(SpringRunner.class)
public class MvpServiceTest {

	private ModelMapper modelMapper = new ModelMapper();

	private MvpService mvpService;

	@MockBean
	private MvpRepository mvpRepository;

	@MockBean
	private TeamRepository teamRepository;

	@Before
	public void setUp() {
		this.mvpService = new MvpService(mvpRepository, teamRepository);
	}

	@Test
	public void whenUnassignTeamOfMvpThenUpdateTeamInMvp() throws ResourceNotFoundException {
		Mvp mockMvp = new Mvp();
		Team mockTeam = new Team();
		mockTeam.setId(1l);
		mockMvp.setId(1l);
		mockTeam.setName("team1");
		mockMvp.setTeam(mockTeam);
		Optional<Mvp> mvp = Optional.ofNullable(mockMvp);

		Mockito.when(mvpRepository.findById(mockMvp.getId())).thenReturn(mvp);
		Mockito.when(mvpRepository.save(mockMvp)).thenReturn(mockMvp);

		// when
		mvpService.unassignTeamOfMvp(mockMvp.getId());

		// then
		Assert.assertNull(mockMvp.getTeam());

	}

	@Test
	public void whenAssignTeamOfMvpThenUpdateTeamInMvp() throws ResourceNotFoundException {
		Mvp mockMvp = new Mvp();
		Team mockTeam = new Team();
		mockTeam.setId(1l);
		mockMvp.setId(1l);
		mockTeam.setName("team1");

		Optional<Mvp> mvp = Optional.ofNullable(mockMvp);
		Optional<Team> team = Optional.ofNullable(mockTeam);

		Mockito.when(mvpRepository.findById(mockMvp.getId())).thenReturn(mvp);
		Mockito.when(teamRepository.findById(mockMvp.getId())).thenReturn(team);
		Mockito.when(mvpRepository.save(mockMvp)).thenReturn(mockMvp);

		// when
		mvpService.assignTeamOfMvp(mockMvp.getId(), mockTeam.getId());

		// then
		Assert.assertNotNull(mockMvp.getTeam());

	}

	@Test
	public void whenGetAllMvpThenReturnMvpList() {
		Mvp mockMvp = new Mvp();
		mockMvp.setId(1l);
		mockMvp.setName("cockpit");
		mockMvp.setEntity("RC");
		mockMvp.setCycle(1);
		mockMvp.setUrlMvpAvatar("http://hulza.files.wordpress.com/2012/07/brave_still_3.jpg");
		List<Mvp> mockMvpList = new ArrayList<>();
		mockMvpList.add(mockMvp);

		// given
		Mockito.when(mvpRepository.findAllByOrderByName()).thenReturn(mockMvpList);

		// when
		List<MvpDTO> mvpList = mvpService.findAllMvp();

		// then
		Assert.assertEquals(1, mvpList.size());
	}

	@Test
	public void whenGetMvpByIdThenReturnMvp() throws ResourceNotFoundException {
		Mvp mockMvp = new Mvp();
		mockMvp.setId(1l);
		mockMvp.setName("cockpit");

		// given
		Mockito.when(mvpRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockMvp));

		// when
		MvpDTO mvp = mvpService.findMvpById(1l);

		// then
		Assert.assertEquals("cockpit", mvp.getName());
	}

	@Test
	public void whenCreateThenReturnMvp() {
		Mvp mockMvp = new Mvp();
		mockMvp.setId(1l);
		mockMvp.setName("cockpit");

		// given
		Mockito.when(mvpRepository.save(Mockito.any())).thenReturn(mockMvp);

		// when
		MvpDTO mvp = mvpService.createNewMvp(modelMapper.map(mockMvp, MvpDTO.class));

		// then
		Assert.assertEquals("cockpit", mvp.getName());
	}
}
