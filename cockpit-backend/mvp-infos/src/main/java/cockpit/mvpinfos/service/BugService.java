package cockpit.mvpinfos.service;

import cockpit.cockpitcore.domaine.db.Bug;
import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.repository.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BugService {

    private BugRepository bugRepository;

    @Autowired
    public BugService(
            BugRepository bugRepository
    ) {
        this.bugRepository = bugRepository;
    }

    public List<Bug> getBugsForAnMvp(Mvp mvp) {
        List<Bug> bugList = bugRepository.findDistinctByMvp(mvp);
        return bugList;
    }

    public List<Bug> findAll() {
        return bugRepository.findAll();
    }
}
