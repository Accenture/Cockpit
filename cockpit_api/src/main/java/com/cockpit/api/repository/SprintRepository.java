package com.cockpit.api.repository;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Sprint;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends CrudRepository<Sprint, Long> {

	Sprint findTopBySprintStartDateLessThanEqualAndJiraEqualsOrderBySprintNumberDesc(Date date, Jira jira);
    Sprint findByJiraAndSprintNumber(Jira jira, int sprintNumber);
    Sprint findByJiraSprintId(int jiraSprintId);
    List<Sprint> findByJira(Jira jira);

}
