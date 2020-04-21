package cockpit.cockpitcore.repository;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.Sprint;
import cockpit.cockpitcore.domaine.db.UserStoriesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStoriesHistoryRepository extends JpaRepository<UserStoriesHistory, String> {


    List<UserStoriesHistory> findAllByUsHistoryCompositeKey_MvpOrderByUsHistoryCompositeKey_Date(Mvp mvp);

    List<UserStoriesHistory> findByUsHistoryCompositeKeyMvpAndSprint(Mvp mvp, Sprint sprint);
}