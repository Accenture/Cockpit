package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dto.TechnologyDTO;
import com.cockpit.api.repository.MvpRepository;
import com.cockpit.api.repository.TechnologyRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class TechnologyServiceTest {
    private TechnologyService technologyService;

    @MockBean
    MvpRepository mvpRepository;

    @MockBean
    TechnologyRepository technologyRepository;

    @Before
    public void setUp() {
        this.technologyService = new TechnologyService(technologyRepository, mvpRepository);
    }

    @Test
    public void whenCreateTechnologyThenReturnCreatedTechnology() throws ResourceNotFoundException {
        Mvp mockMvp = new Mvp();
        mockMvp.setId(1l);
        mockMvp.setTechnologies(new HashSet<>());
        TechnologyDTO mockTechnology = new TechnologyDTO();
        mockTechnology.setName("JAVA");
        mockTechnology.setUrl("https://www.tc-web.it/wp-content/uploads/2019/12/java.jpg");
        Optional<Mvp> mvp = Optional.ofNullable(mockMvp);

        // given
        Mockito.when(mvpRepository.findById(mockMvp.getId())).thenReturn(mvp);

        // when
        technologyService.createNewTechnology(mockTechnology, mockMvp.getId());

        // then
        Assert.assertTrue(!mockMvp.getTechnologies().isEmpty());

    }
}
