package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dao.Technology;
import com.cockpit.api.model.dto.TechnologyDTO;
import com.cockpit.api.repository.MvpRepository;
import com.cockpit.api.repository.TechnologyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TechnologyService {
    private final TechnologyRepository technologyRepository;
    private final MvpRepository mvpRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public TechnologyService(TechnologyRepository technologyRepository, MvpRepository mvpRepository) {
        this.technologyRepository = technologyRepository;
        this.mvpRepository=mvpRepository;
    }

    public TechnologyDTO createNewTechnology(TechnologyDTO technologyDTO, Long mvpId) throws ResourceNotFoundException {
        Optional<Mvp> mvp = mvpRepository.findById(mvpId);
        if (!mvp.isPresent()) {
            throw new ResourceNotFoundException("mvp not found");
        }
            mvp.get().getTechnologies().add(modelMapper.map(technologyDTO, Technology.class));
            mvpRepository.save(mvp.get());

        return technologyDTO ;
    }
    public TechnologyDTO assignTechnology(long technoId, Long mvpId) throws ResourceNotFoundException {
        Optional<Mvp> mvp = mvpRepository.findById(mvpId);
        if (!mvp.isPresent()) {
            throw new ResourceNotFoundException("mvp not found");
        }
        Optional<Technology> technology = technologyRepository.findById(technoId);
        if (!technology.isPresent()) {
            throw new ResourceNotFoundException("technology not found");
        }
        mvp.get().getTechnologies().add(technology.get());
        mvpRepository.save(mvp.get());

        return modelMapper.map(technology, TechnologyDTO.class);
    }
    public TechnologyDTO unassignTechnology(long technoId, Long mvpId) throws ResourceNotFoundException {
        Optional<Mvp> mvp = mvpRepository.findById(mvpId);
        if (!mvp.isPresent()) {
            throw new ResourceNotFoundException("mvp not found");
        }
        Optional<Technology> technology = technologyRepository.findById(technoId);
        if (!technology.isPresent()) {
            throw new ResourceNotFoundException("technology not found");
        }
        mvp.get().getTechnologies().remove(technology.get());
        mvpRepository.save(mvp.get());

        return modelMapper.map(technology, TechnologyDTO.class);
    }
    public TechnologyDTO findTechnologyById(Long id) throws ResourceNotFoundException {
        Optional<Technology> technologyRes = technologyRepository.findById(id);
        if (!technologyRes.isPresent()) {
            throw new ResourceNotFoundException("Technology not found");
        }
        return modelMapper.map(technologyRes.get(), TechnologyDTO.class);
    }

    public List<TechnologyDTO> findAllTechnology(){
        List<Technology> technologyList = technologyRepository.findAllByOrderByName();
        return technologyList.stream().map(technology -> modelMapper.map(technology, TechnologyDTO.class)).collect(Collectors.toList());
    }

    public TechnologyDTO updateTechnology(TechnologyDTO technologyDTO, Long id) throws ResourceNotFoundException {
        Optional<Technology> technologyToUpdate = technologyRepository.findById(id);
        if (!technologyToUpdate.isPresent()) {
            throw new ResourceNotFoundException("The technology to be updated does not exist in database");
        }
        technologyDTO.setId(technologyToUpdate.get().getId());
        Technology technologyCreated = technologyRepository.save(modelMapper.map(technologyDTO, Technology.class));
        return modelMapper.map(technologyCreated, TechnologyDTO.class);
    }

    public void deleteTechnology(Long id) throws ResourceNotFoundException {
        Optional<Technology> technologyToDelete = technologyRepository.findById(id);
        if (!technologyToDelete.isPresent()) {
            throw new ResourceNotFoundException("The technology to be deleted does not exist in database");
        }
        technologyRepository.delete(technologyToDelete.get());
    }
}
