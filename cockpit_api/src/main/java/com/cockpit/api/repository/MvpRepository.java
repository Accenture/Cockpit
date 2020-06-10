package com.cockpit.api.repository;

import com.cockpit.api.model.dao.Mvp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MvpRepository extends CrudRepository<Mvp, Long> {
    List<Mvp> findAllByOrderByName();
    Mvp findByName(String name);
}