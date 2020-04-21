package cockpit.cockpitcore.repository;

import cockpit.cockpitcore.domaine.db.BugHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugHistoryRepository extends JpaRepository<BugHistory, String> {

}

