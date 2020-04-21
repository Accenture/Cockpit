package cockpit.mvpinfos.service;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.TechnicalDebt;
import cockpit.cockpitcore.repository.TechnicalDebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicalDebtService {

    private TechnicalDebtRepository technicalDebtRepository;
    @Autowired
    public  TechnicalDebtService(TechnicalDebtRepository technicalDebtRepository) {
        this.technicalDebtRepository = technicalDebtRepository;
    }

    public TechnicalDebt getTechnicalDebtForAnMvp (Mvp mvp) {
        TechnicalDebt technicalDebt = technicalDebtRepository.findByMvp(mvp);

        return technicalDebt;
    }

    public List<TechnicalDebt> getAllTechnicalDebts() {
        return technicalDebtRepository.findAll();
    }
}
