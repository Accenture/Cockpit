package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Impediment;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.ImpedimentDTO;
import com.cockpit.api.repository.ImpedimentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class ImpedimentServiceTest {
    private final ModelMapper modelMapper = new ModelMapper();

    private ImpedimentService impedimentService;

    @MockBean
    private ImpedimentRepository impedimentRepository;


    @Before
    public void setUp() {
        this.impedimentService = new ImpedimentService(impedimentRepository);
    }

    @Test
    public void whenDeleteImpedimentThenDeleteImpedimentFromSprint() throws ResourceNotFoundException {
        Impediment mockImpediment = new Impediment("impediment name", "impediment description");
        mockImpediment.setId(1L);
        Sprint sprint = new Sprint();
        sprint.setId(1L);
        sprint.setImpediments(new HashSet<>());
        mockImpediment.setSprint(sprint);
        Optional<Impediment> impediment = Optional.ofNullable(mockImpediment);

        // Given
        Mockito.when(impedimentRepository.findById(mockImpediment.getId())).thenReturn(impediment);

        // When
        impedimentService.deleteImpediment(mockImpediment.getId());

        // Then
        Assert.assertTrue(sprint.getImpediments().isEmpty());
    }

    @Test
    public void whenUpdateImpedimentThenReturnUpdatedImpediment() throws ResourceNotFoundException {
        Impediment updatedImpediment = new Impediment();
        updatedImpediment.setId(1L);
        Sprint sprint = new Sprint();
        sprint.setId(1L);
        sprint.setImpediments(new HashSet<>());
        updatedImpediment.setSprint(sprint);
        Optional<Impediment> impediment = Optional.ofNullable(updatedImpediment);

        Impediment mockImpediment = new Impediment("new name", "new description");
        ImpedimentDTO impedimentDTO = modelMapper.map(mockImpediment, ImpedimentDTO.class);

        // Given
        Mockito.when(impedimentRepository.findById(mockImpediment.getId())).thenReturn(impediment);
        Mockito.when(impedimentRepository.save(Mockito.any(Impediment.class))).thenReturn(updatedImpediment);

        // When
        ImpedimentDTO impedimentCreated = impedimentService.updateImpediment(impedimentDTO, mockImpediment.getId());

        // Then
        Assert.assertNotNull(impedimentCreated);
    }
}
