package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dto.JiraDTO;
import com.cockpit.api.repository.JiraRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class JiraService {
    private final JiraRepository jiraRepository;

    private ModelMapper modelMapper = new ModelMapper();
    
    @Autowired
    public JiraService(JiraRepository jiraRepository) {
        this.jiraRepository = jiraRepository;
    }

    public JiraDTO createNewJiraProject(JiraDTO jiraDTO){
        Jira jiraCreated = jiraRepository.save(modelMapper.map(jiraDTO, Jira.class));
        return modelMapper.map(jiraCreated, JiraDTO.class);
    }

    public JiraDTO findJiraById(Long id) throws ResourceNotFoundException {
        Optional<Jira> jiraRes = jiraRepository.findById(id);
        if (!jiraRes.isPresent()) {
            throw new ResourceNotFoundException("Jira not found");
        }
        return modelMapper.map(jiraRes.get(), JiraDTO.class);
    }

    public JiraDTO updateJira(JiraDTO jiraDTO) throws ResourceNotFoundException {
        Jira jiraToUpdate = jiraRepository.findByJiraProjectKey(jiraDTO.getJiraProjectKey());
        if (jiraToUpdate == null) {
            throw new ResourceNotFoundException("Jira not found");
        }
        Jira jiraCreated = jiraRepository.save(modelMapper.map(jiraDTO, Jira.class));
        return modelMapper.map(jiraCreated, JiraDTO.class);
    }

    public void deleteJira(Long id) throws ResourceNotFoundException {
        Optional<Jira> jiraToDelete = jiraRepository.findById(id);
        if (!jiraToDelete.isPresent()) {
            throw new ResourceNotFoundException("Jira not found");
        }
        jiraRepository.delete(jiraToDelete.get());
    }
    Jira findByMvp(Mvp mvp)
    {
    	return jiraRepository.findByMvp(mvp);
    }
}
