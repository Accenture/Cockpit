package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dao.Team;
import com.cockpit.api.model.dto.MvpDTO;

import com.cockpit.api.repository.MvpRepository;
import com.cockpit.api.repository.TeamRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MvpService {

	private final MvpRepository mvpRepository;
	private final TeamRepository teamRepository;
	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	public MvpService(MvpRepository mvpRepository, TeamRepository teamRepository) {
		this.mvpRepository = mvpRepository;
		this.teamRepository = teamRepository;
	}

	public MvpDTO createNewMvp(MvpDTO mvpDTO) {
		Mvp mvpCreated = mvpRepository.save(modelMapper.map(mvpDTO, Mvp.class));
		return modelMapper.map(mvpCreated, MvpDTO.class);
	}

	public MvpDTO findMvpById(Long id) throws ResourceNotFoundException {
		Optional<Mvp> mvpRes = mvpRepository.findById(id);
		if (!mvpRes.isPresent()) {
			throw new ResourceNotFoundException("Mvp not found");
		}
		return modelMapper.map(mvpRes.get(), MvpDTO.class);
	}

	public List<MvpDTO> findAllMvp() {
		List<Mvp> mvpList = mvpRepository.findAllByOrderByName();
		return mvpList.stream().map(mvp -> modelMapper.map(mvp, MvpDTO.class)).collect(Collectors.toList());
	}

	public MvpDTO assignTeamOfMvp(Long id, Long teamId) throws ResourceNotFoundException {
		Optional<Mvp> mvpToAssignedTo = mvpRepository.findById(id);
		if (!mvpToAssignedTo.isPresent()) {
			throw new ResourceNotFoundException("Mvp to assign to is not found");
		}

		Optional<Team> team = teamRepository.findById(teamId);
		if (!team.isPresent()) {
			throw new ResourceNotFoundException("team to be assigned is not found");
		}
		mvpToAssignedTo.get().setTeam(team.get());
		Mvp updatedtMvp = mvpRepository.save(mvpToAssignedTo.get());
		return modelMapper.map(updatedtMvp, MvpDTO.class);
	}
	public MvpDTO unassignTeamOfMvp(Long id) throws ResourceNotFoundException {
		Optional<Mvp> mvp = mvpRepository.findById(id);
		if (!mvp.isPresent()) {
			throw new ResourceNotFoundException("Mvp to unassign to is not found");
		}

		
		mvp.get().setTeam(null);
		Mvp updatedtMvp = mvpRepository.save(mvp.get());
		return modelMapper.map(updatedtMvp, MvpDTO.class);
	}
}
