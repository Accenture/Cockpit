package com.cockpit.api.repository;

import com.cockpit.api.model.dao.Technology;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnologyRepository extends CrudRepository<Technology, Long> {
    List<Technology> findAllByOrderByName();
}
