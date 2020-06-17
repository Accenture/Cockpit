package com.cockpit.api.repository;

import java.util.Date;
import java.util.List;
import com.cockpit.api.model.dao.Jira;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cockpit.api.model.dao.UserStory;

@Repository
public interface UserStoryRepository extends CrudRepository<UserStory, Long> {
	@Query("select COUNT(us) FROM UserStory us, Sprint sp WHERE us.sprint.id = sp.id AND sp.jira = (:jira) AND us.status='DONE' and sp.sprintNumber<=(:sprintNumber)")
	Integer countNumberOfClosedUsPerSprint(@Param("jira") Jira jira, @Param("sprintNumber") int sprintNumber);

	@Query("SELECT us FROM UserStory us, Sprint sp WHERE us.sprint.id = sp.id AND sp.jira = (:jira) AND sp.sprintNumber" +
			" = (:sprintNumber)")
	List<UserStory> findMyUserStories(@Param("jira") Jira jira, @Param("sprintNumber") int sprintNumber);

	UserStory findByIssueKey(String issueKey);

	int countUserStoriesByJiraAndCreationDateBetween(Jira jira, Date sprintStartDate, Date sprintEndDate);

	int countUserStoriesByJiraAndCreationDateBefore(Jira jira, Date firstSprintStartDate);

}
