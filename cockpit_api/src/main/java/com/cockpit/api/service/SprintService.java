package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.SprintDTO;
import com.cockpit.api.repository.SprintRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class SprintService {
    private final SprintRepository sprintRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public SprintDTO createNewSprint(SprintDTO sprintDTO){
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
        int sprintId = 0;
        if (sprint != null) {
            sprintId = sprint.getSprintNumber();

        }
        return sprintId;
    }
    Sprint findByMvpAndSprintNumber(Jira jira, int sprintNumber)
    {
    	return sprintRepository.findByJiraAndSprintNumber(jira, sprintNumber);
    }


}
