package com.cockpit.api.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dao.Team;
import com.cockpit.api.repository.MvpRepository;
import com.cockpit.api.repository.TeamRepository;
import com.cockpit.api.service.MvpService;

@RunWith(SpringRunner.class)
public class MvpServiceTest {

	private MvpService mvpService;

    @MockBean
    private MvpRepository mvpRepository;
    
    @MockBean
    private TeamRepository teamRepository;
    
    @Before
    public void setUp() {
        this.mvpService=  new MvpService(mvpRepository, teamRepository);
    }
    

    @Test
    public void WhenUnassignTeamOfMvp_thenUpdateTeamInMvp() throws ResourceNotFoundException {
    	Mvp mockMvp = new Mvp();    
        Team mockTeam = new Team();
        mockTeam.setId(1l);
        mockMvp.setId(1l);
        mockTeam.setName("team1");
        mockMvp.setTeam(mockTeam);
    	Optional<Mvp> mvp =	 Optional.ofNullable(mockMvp);
    	
        Mockito.when(mvpRepository.findById(mockMvp.getId())).thenReturn(mvp);
        Mockito.when(mvpRepository.save(mockMvp)).thenReturn(mockMvp);
        
        // when
        mvpService.unassignTeamOfMvp(mockMvp.getId());

        // then
        Assert.assertNull(mockMvp.getTeam());
        
        
    }
}
