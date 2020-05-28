package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Team;
import com.cockpit.api.model.dto.TeamDTO;
import com.cockpit.api.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamDTO createNewTeam(TeamDTO teamDTO){
        Team teamCreated = teamRepository.save(modelMapper.map(teamDTO, Team.class));
        return modelMapper.map(teamCreated, TeamDTO.class);
    }

    public TeamDTO findTeamById(Long id) throws ResourceNotFoundException {
        Optional<Team> teamRes = teamRepository.findById(id);
        if (!teamRes.isPresent()) {
            throw new ResourceNotFoundException("Team not found");
        }
        return modelMapper.map(teamRes.get(), TeamDTO.class);
    }

    public TeamDTO updateTeam(TeamDTO teamDTO, Long id) throws ResourceNotFoundException {
        Optional<Team> teamToUpdate = teamRepository.findById(id);
        if (!teamToUpdate.isPresent()) {
            throw new ResourceNotFoundException("The team to be updated does not exist in database");
        }
        teamDTO.setId(teamToUpdate.get().getId());
        Team teamCreated = teamRepository.save(modelMapper.map(teamDTO, Team.class));
        return modelMapper.map(teamCreated, TeamDTO.class);
    }

    public void deleteTeam(Long id) throws ResourceNotFoundException {
        Optional<Team> teamToDelete = teamRepository.findById(id);
        if (!teamToDelete.isPresent()) {
            throw new ResourceNotFoundException("The team to be deleted does not exist in database");
        }
        teamRepository.delete(teamToDelete.get());
    }
}
