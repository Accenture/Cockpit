package cockpit.cockpitcore.repository;

import cockpit.cockpitcore.domaine.db.Bug;
import cockpit.cockpitcore.domaine.db.Mvp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BugRepository extends JpaRepository<Bug, String> {
    Optional<Bug> findByIssueKey(String issueKey);

    List<Bug> findDistinctByMvp(Mvp mvp);
}
