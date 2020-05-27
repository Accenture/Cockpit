package com.cockpit.api.repository;

import com.cockpit.api.model.dao.Sprint;
import org.springframework.data.repository.CrudRepository;

public interface SprintRepository extends CrudRepository<Sprint, Long> {
}
