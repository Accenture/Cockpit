package com.cockpit.api.repository;

import com.cockpit.api.model.dao.Jira;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JiraRepository extends CrudRepository<Jira, Long> {
    Jira findByJiraProjectKey(String name);
}
