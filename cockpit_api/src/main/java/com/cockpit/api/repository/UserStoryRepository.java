package com.cockpit.api.repository;

import com.cockpit.api.model.dao.UserStory;
import org.springframework.data.repository.CrudRepository;

public interface UserStoryRepository extends CrudRepository<UserStory, Long> {
}
