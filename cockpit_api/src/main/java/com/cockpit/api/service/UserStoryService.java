package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.model.dto.UserStoryDTO;
import com.cockpit.api.repository.UserStoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserStoryService {
    private final UserStoryRepository userStoryRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserStoryService(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    public UserStoryDTO createNewUserStory(UserStoryDTO userStoryDTO){
        UserStory userStoryCreated = userStoryRepository.save(modelMapper.map(userStoryDTO, UserStory.class));
        return modelMapper.map(userStoryCreated, UserStoryDTO.class);
    }

    public UserStoryDTO findUserStoryById(Long id) throws ResourceNotFoundException {
        Optional<UserStory> userStoryRes = userStoryRepository.findById(id);
        if (!userStoryRes.isPresent()) {
            throw new ResourceNotFoundException("User Story not found");
        }
        return modelMapper.map(userStoryRes.get(), UserStoryDTO.class);
    }

    public UserStoryDTO updateUserStory(UserStoryDTO userStoryDTO, Long id) throws ResourceNotFoundException {
        Optional<UserStory> userStoryToUpdate = userStoryRepository.findById(id);
        if (!userStoryToUpdate.isPresent()) {
            throw new ResourceNotFoundException("The User Story to be updated does not exist in database");
        }
        userStoryDTO.setId(userStoryToUpdate.get().getId());
        UserStory userStoryCreated = userStoryRepository.save(modelMapper.map(userStoryDTO, UserStory.class));
        return modelMapper.map(userStoryCreated, UserStoryDTO.class);
    }

    public void deleteUserStory(Long id) throws ResourceNotFoundException {
        Optional<UserStory> userStoryToDelete = userStoryRepository.findById(id);
        if (!userStoryToDelete.isPresent()) {
            throw new ResourceNotFoundException("The User Story to be deleted does not exist in database");
        }
        userStoryRepository.delete(userStoryToDelete.get());
    }
}
