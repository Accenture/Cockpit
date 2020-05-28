package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dto.MvpDTO;
import com.cockpit.api.repository.MvpRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MvpService {

    private final MvpRepository mvpRepository;

    private ModelMapper modelMapper = new ModelMapper();


    @Autowired
    public MvpService(MvpRepository mvpRepository) {
        this.mvpRepository = mvpRepository;
    }

    public MvpDTO createNewMvp(MvpDTO mvpDTO){
        Mvp mvpCreated = mvpRepository.save(modelMapper.map(mvpDTO, Mvp.class));
        return modelMapper.map(mvpCreated, MvpDTO.class);
    }

    public MvpDTO findMvpById(Long id) throws ResourceNotFoundException{
        Optional<Mvp> mvpRes = mvpRepository.findById(id);
        if (!mvpRes.isPresent()) {
            throw new ResourceNotFoundException("Mvp not found");
        }
        return modelMapper.map(mvpRes.get(), MvpDTO.class);
    }

    public List<MvpDTO> findAllMvp(){
        List<Mvp> mvpList = mvpRepository.findAllByOrderByName();
        return mvpList.stream().map(mvp -> modelMapper.map(mvp, MvpDTO.class)).collect(Collectors.toList());
    }

    public MvpDTO updateMvp(MvpDTO mvpDTO){
        Mvp mvpToUpdate = mvpRepository.findByName(mvpDTO.getName());
        mvpDTO.setId(mvpToUpdate.getId());
        Mvp mvpCreated = mvpRepository.save(modelMapper.map(mvpDTO, Mvp.class));
        return modelMapper.map(mvpCreated, MvpDTO.class);
    }

    public void deleteMvp(Long id) throws ResourceNotFoundException {
        Optional<Mvp> mvpToDelete = mvpRepository.findById(id);
        if (!mvpToDelete.isPresent()) {
            throw new ResourceNotFoundException("Mvp not found");
        }
        mvpRepository.delete(mvpToDelete.get());
    }
}
