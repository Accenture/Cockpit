package com.cockpit.api.repository;

import com.cockpit.api.model.dao.Jira;
import com.cockpit.api.model.dao.Mvp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JiraRepository extends CrudRepository<Jira, Long> {
    Jira findByJiraProjectKey(String key);
    Jira findByMvp(Mvp mvp);
    List<Jira> findAllByOrderById();
    Optional<Jira> findByJiraProjectId(Integer id);
}
