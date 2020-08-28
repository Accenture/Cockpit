package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Impediment;
import com.cockpit.api.model.dto.ImpedimentDTO;
import com.cockpit.api.repository.ImpedimentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImpedimentService {

    private final ImpedimentRepository impedimentRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ImpedimentService(ImpedimentRepository impedimentRepository) {
        this.impedimentRepository = impedimentRepository;
    }

    public void deleteImpediment(Long id) throws ResourceNotFoundException {
        Optional<Impediment> impedimentToDelete = impedimentRepository.findById(id);
        if (!impedimentToDelete.isPresent()) {
            throw new ResourceNotFoundException("The sprint to be deleted does not exist in database");
        }
        impedimentRepository.delete(impedimentToDelete.get());
    }

    public ImpedimentDTO updateImpediment(ImpedimentDTO impedimentDTO, Long id) throws ResourceNotFoundException {
        Optional<Impediment> impedimentToUpdate = impedimentRepository.findById(id);
        if (!impedimentToUpdate.isPresent()) {
            throw new ResourceNotFoundException("The sprint to be updated does not exist in database");
        }
        impedimentDTO.setId(impedimentToUpdate.get().getId());
        impedimentDTO.setSprint(impedimentToUpdate.get().getSprint());
        Impediment impedimentCreated = impedimentRepository.save(modelMapper.map(impedimentDTO, Impediment.class));
        return modelMapper.map(impedimentCreated, ImpedimentDTO.class);
    }
}
