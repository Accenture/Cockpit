package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Impediment;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.ImpedimentDTO;
import com.cockpit.api.model.dto.ObeyaDTO;
import com.cockpit.api.model.dto.SprintDTO;
import com.cockpit.api.repository.ImpedimentRepository;
import com.cockpit.api.repository.SprintRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class SprintService {
    private final SprintRepository sprintRepository;
    private final ImpedimentRepository impedimentRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public SprintService(SprintRepository sprintRepository, ImpedimentRepository impedimentRepository) {
        this.sprintRepository = sprintRepository;
        this.impedimentRepository = impedimentRepository;
    }

    public SprintDTO createNewSprint(SprintDTO sprintDTO) {
        Sprint sprintCreated = sprintRepository.save(modelMapper.map(sprintDTO, Sprint.class));
        return modelMapper.map(sprintCreated, SprintDTO.class);
    }

    public SprintDTO findSprintById(Long id) throws ResourceNotFoundException {
        Optional<Sprint> sprintRes = sprintRepository.findById(id);
        if (!sprintRes.isPresent()) {
            throw new ResourceNotFoundException("Sprint not found");
        }
        return modelMapper.map(sprintRes.get(), SprintDTO.class);
    }

    public SprintDTO updateSprint(SprintDTO sprintDTO, Long id) throws ResourceNotFoundException {
        Optional<Sprint> sprintToUpdate = sprintRepository.findById(id);
        if (!sprintToUpdate.isPresent()) {
            throw new ResourceNotFoundException("The sprint to be updated does not exist in database");
        }
        sprintDTO.setId(sprintToUpdate.get().getId());
        Sprint sprintCreated = sprintRepository.save(modelMapper.map(sprintDTO, Sprint.class));
        return modelMapper.map(sprintCreated, SprintDTO.class);
    }

    public void deleteSprint(Long id) throws ResourceNotFoundException {
        Optional<Sprint> sprintToDelete = sprintRepository.findById(id);
        if (!sprintToDelete.isPresent()) {
            throw new ResourceNotFoundException("The sprint to be deleted does not exist in database");
        }
        sprintRepository.delete(sprintToDelete.get());
    }

    public int findSprintNumberForADate(Jira jira, Date date) {
        Sprint sprint = sprintRepository.findTopBySprintStartDateLessThanEqualAndJiraEqualsOrderBySprintNumberDesc(date, jira);
        int sprintNumber = 0;
        if (sprint != null) {
            sprintNumber = sprint.getSprintNumber();
        }
        return sprintNumber;
    }

    public Sprint findByJiraAndSprintNumber(Jira jira, int sprintNumber) {
        return sprintRepository.findByJiraAndSprintNumber(jira, sprintNumber);
    }

    public SprintDTO setTeamHealth(ObeyaDTO obeya, Sprint sprint) {
        sprint.setTeamMood(obeya.getTeamMood());
        sprint.setTeamMotivation(obeya.getTeamMotivation());
        sprint.setTeamConfidence(obeya.getTeamConfidence());
        return modelMapper.map(sprintRepository.save(sprint), SprintDTO.class);

    }

    public SprintDTO addImpediment(Impediment impediment, Sprint sprint) {
        impediment.setSprint(sprint);
        modelMapper.map(impedimentRepository.save(impediment), ImpedimentDTO.class);
        return modelMapper.map(sprintRepository.save(sprint), SprintDTO.class);
    }
}
