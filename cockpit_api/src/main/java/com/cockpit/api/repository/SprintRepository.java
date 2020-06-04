package com.cockpit.api.repository;

<<<<<<< HEAD
import com.cockpit.api.model.dao.Mvp;
import com.cockpit.api.model.dao.Sprint;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends CrudRepository<Sprint, Long> {

	Sprint findTopBySprintStartDateLessThanEqualAndMvpEqualsOrderBySprintNumberDesc(Date date, Mvp mvp);
    Sprint findByMvpAndSprintNumber(Mvp mvp, int sprintNumber);

=======
import com.cockpit.api.model.dao.Sprint;
import org.springframework.data.repository.CrudRepository;

public interface SprintRepository extends CrudRepository<Sprint, Long> {
>>>>>>> CP-73-createNewBackend
}
