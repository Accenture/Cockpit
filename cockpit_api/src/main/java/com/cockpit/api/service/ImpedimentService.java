package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Impediment;
import com.cockpit.api.repository.ImpedimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImpedimentService {

    private final ImpedimentRepository impedimentRepository;

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
}
