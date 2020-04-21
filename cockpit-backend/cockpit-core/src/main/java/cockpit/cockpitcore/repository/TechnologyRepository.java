package cockpit.cockpitcore.repository;

import cockpit.cockpitcore.domaine.db.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, String> {
    Technology findByName(String name);
}
