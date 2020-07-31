package com.cockpit.api.repository;

import com.cockpit.api.model.dao.Impediment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpedimentRepository extends CrudRepository<Impediment, Long>{
}
