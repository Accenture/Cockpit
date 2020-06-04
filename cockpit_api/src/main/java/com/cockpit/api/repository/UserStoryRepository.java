package com.cockpit.api.repository;

<<<<<<< HEAD
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dao.UserStory;
@Repository
public interface UserStoryRepository extends CrudRepository<UserStory, Long> {
	  @Query("select COUNT(us) FROM UserStory us, Sprint sp WHERE us.sprint = sp.id AND sp.mvp = (:mvp) AND us.status='Done' and sp.sprintNumber<=(:sprintNumber)")
	  Integer countNumberOfClosedUsPerSprint(@Param("mvp") Mvp mvp, @Param("sprintNumber") int SprintNumber);
	  
	   @Query("SELECT us FROM UserStory us, Sprint sp WHERE us.sprint = sp.id AND sp.mvp = (:mvp) AND sp.sprintNumber" +
	            " = (:sprintNumber)")
	    List<UserStory> findMyUserStories(@Param("mvp") Mvp mvp, @Param("sprintNumber") int SprintNumber);
=======
import com.cockpit.api.model.dao.UserStory;
import org.springframework.data.repository.CrudRepository;

public interface UserStoryRepository extends CrudRepository<UserStory, Long> {
>>>>>>> CP-73-createNewBackend
}
