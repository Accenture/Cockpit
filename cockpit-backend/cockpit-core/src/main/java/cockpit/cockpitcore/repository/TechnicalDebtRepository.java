package cockpit.cockpitcore.repository;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.TechnicalDebt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicalDebtRepository extends JpaRepository<TechnicalDebt , String> {
    TechnicalDebt findByMvp(Mvp mvp);

    List<TechnicalDebt> findAll();
}
