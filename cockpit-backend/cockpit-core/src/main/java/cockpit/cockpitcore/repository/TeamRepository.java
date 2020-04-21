package cockpit.cockpitcore.repository;

import cockpit.cockpitcore.domaine.db.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {

	
	List<Team> findByMvps_Id(Integer mvpId);

    List<Team> findAll();

    Team findByName(String name);

}
