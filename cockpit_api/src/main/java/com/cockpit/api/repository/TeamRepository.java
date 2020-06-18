package com.cockpit.api.repository;

import com.cockpit.api.model.dao.Team;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
	
	
    List<Team> findAllByOrderByName();
}
