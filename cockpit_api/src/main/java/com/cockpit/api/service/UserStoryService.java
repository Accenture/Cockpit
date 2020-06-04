package com.cockpit.api.service;

import com.cockpit.api.exception.ResourceNotFoundException;
import com.cockpit.api.model.dao.UserStory;
import com.cockpit.api.model.dto.UserStoryDTO;
import com.cockpit.api.repository.UserStoryRepository;

import com.cockpit.api.model.dao.Sprint;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.cockpit.api.model.dao.Mvp;

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
    public Integer findSumOfUsClosedForSprint(Mvp mvp, int sprintNumber) {

        return userStoryRepository.countNumberOfClosedUsPerSprint(mvp, sprintNumber);
    }
    public int getMaxNumberOfStoriesForADateOfAnMvp(Sprint sprint, Mvp mvp) {
        int totalStories = 0;
        if (mvp != null && sprint != null) {
            List<UserStory> userStoriesList = userStoryRepository.findMyUserStories(mvp, sprint.getSprintNumber());

            if(userStoriesList != null && !userStoriesList.isEmpty())
            {
                totalStories = userStoriesList.size();
            }
        }
        return totalStories;
    }
}
