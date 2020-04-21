package cockpit.cockpitcore.repository;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@EnableJpaRepositories
@Repository
public interface SprintRepository extends JpaRepository<Sprint, String> {

    List<Sprint> findByMvp(Mvp mvp);

    Sprint findByMvpAndSprintNumber(Mvp mvp, int sprintNumber);

    List<Sprint> findByMvpOrderById(Mvp mvp);

    Sprint findByJiraSprintId(int jiraSprintId);

    Sprint findById(int id);

    Sprint findTopBySprintStartDateLessThanEqualAndMvpEqualsOrderBySprintNumberDesc(@Param("date") Date date, @Param("mvp") Mvp mvp);

}
