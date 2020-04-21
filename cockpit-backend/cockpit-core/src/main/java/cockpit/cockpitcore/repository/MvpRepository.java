package cockpit.cockpitcore.repository;

import cockpit.cockpitcore.domaine.db.Mvp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MvpRepository extends JpaRepository<Mvp, String> {

    Optional<Mvp> findById(String id);

    List<Mvp> findAllByOrderByMvpStartDateDesc();

    List<Mvp> findAllByOrderByName();

    Optional<Mvp> findByJiraProjectId(int key);

    @Query(value = ""
            + "SELECT final.id, final.status, final.location, final.entity, final.name, final.mvpavatarurl, final.iteration_number, final.currentsprint, S.teammood, S.teammotivation, S.confidenttarget, final.needsupdate, final.jiraprojectid "
            + "FROM (SELECT M.id, M.status, M.location, M.entity, M.name, M.mvpavatarurl, M.iteration_number, M.currentsprint, M.jiraprojectid, CASE WHEN ct IS NULL THEN 0 WHEN ct >= 1 then 1 END AS needsupdate FROM mvp M "
            + "LEFT JOIN (SELECT COUNT(*) AS ct, mvp_id "
            + "FROM sprint WHERE id NOT IN (SELECT id FROM (SELECT MAX(id), id FROM sprint GROUP BY mvp_id) A) "
            + "AND (teammood = 0 AND confidenttarget = 0 AND teammotivation = 0) GROUP BY mvp_id) B ON M.id = B.mvp_id) final "
            + "LEFT JOIN sprint S on final.id=S.mvp_id "
            + "WHERE S.sprintnumber = final.currentsprint - 1 or S.sprintnumber is null or final.currentsprint = 0 AND final.jiraprojectid is not null", nativeQuery = true)
    List<Object[]> findAllLightMVPs();
}