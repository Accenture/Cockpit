package cockpit.cockpitcore.repository;

import cockpit.cockpitcore.domaine.db.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer> {
	
	
    Set<TeamMember> findAllByOrderByLastName();

    Set<TeamMember> findByTeams_IdTeam(Integer teamId);
}
