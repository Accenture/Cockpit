package cockpit.cockpitcore.repository;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, String> {

    @Query("SELECT us FROM UserStory us, Sprint sp WHERE us.sprint = sp.id AND sp.mvp = (:mvp) AND sp.sprintNumber" +
            " = (:sprintNumber)")
    List<UserStory> findMyUserStories(@Param("mvp") Mvp mvp, @Param("sprintNumber") int SprintNumber);


    @Query("select COUNT(us) FROM UserStory us, Sprint sp WHERE us.sprint = sp.id AND sp.mvp = (:mvp) AND us.status='Done' and sp.sprintNumber<=(:sprintNumber)")
    Long countNumberOfClosedUsPerSprint(@Param("mvp") Mvp mvp, @Param("sprintNumber") int SprintNumber);

    Optional<UserStory> findByJiraIssueId(Integer jiraIssueId);

    Optional<UserStory> findByIssueKey(String issueKey);


    @Query("select COUNT(us) FROM UserStory us, Sprint sp WHERE us.sprint = sp.id AND sp.mvp = (:mvp) AND us.status='Done'")
    Long countNumberOfClosedUsPerMVP(@Param("mvp") Mvp mvp);


    @Query("select COUNT(us) FROM UserStory us, Sprint sp WHERE us.sprint = sp.id AND sp.mvp = (:mvp)")
    Long countNumberOfUsPerMVP(@Param("mvp") Mvp mvp);


    List<UserStory>  findAllByMvp(Mvp mvp);


    void deleteAllByIssueKeyNotIn(List<String> toBeDeletedUS);


}
