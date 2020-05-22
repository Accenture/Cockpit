package com.cockpit.api.repository;


import com.cockpit.api.model.Mvp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MvpRepository extends CrudRepository<Mvp, Long> {

}