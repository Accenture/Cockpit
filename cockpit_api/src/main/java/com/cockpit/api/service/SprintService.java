package com.cockpit.api.service;

import com.cockpit.api.model.dao.Impediment;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import com.cockpit.api.model.dto.ObeyaDTO;
import com.cockpit.api.model.dto.SprintDTO;
import com.cockpit.api.repository.ImpedimentRepository;
import com.cockpit.api.repository.SprintRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

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
        impedimentRepository.save(impediment);
        return modelMapper.map(sprint, SprintDTO.class);
    }
}
