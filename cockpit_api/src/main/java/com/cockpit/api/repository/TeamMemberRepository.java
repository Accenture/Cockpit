package com.cockpit.api.repository;

import com.cockpit.api.model.dao.TeamMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends CrudRepository<TeamMember, Long> {
    List<TeamMember> findAllByOrderById();
}