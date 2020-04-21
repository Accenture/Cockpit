package cockpit.mvpinfos.service;

import cockpit.cockpitcore.domaine.db.Demo;
import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.Sprint;
import cockpit.mvpinfos.exception.ResourceNotFoundException;
import cockpit.cockpitcore.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SprintService {

    private SprintRepository sprintRepository;
    @Autowired
    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public List<Sprint> getSprintListByMvp(Mvp mvp) throws ResourceNotFoundException {
        List<Sprint> sprintList;
        sprintList = sprintRepository.findByMvp(mvp);
        if (sprintList == null) {
            throw new ResourceNotFoundException("getSprintListByMvp: Sprint Repository returned null");
        }
        return sprintList;
    }

    // return found sprint else return null
    public Sprint findSprintByMvpBySprintNumber(Mvp mvp, int sprintNumber) {
        Sprint foundSprint;
        foundSprint = sprintRepository.findByMvpAndSprintNumber(mvp, sprintNumber);
        return foundSprint;
    }

    public int findSprintNumberForADate(Mvp mvp, Date date) {
        Sprint sprint = sprintRepository.findTopBySprintStartDateLessThanEqualAndMvpEqualsOrderBySprintNumberDesc(date, mvp);
        int sprintId = 0;
        if (sprint != null) {
            sprintId = sprint.getSprintNumber();

        }
        return sprintId;
    }

    public Sprint updateSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    public Sprint updateProperties(Sprint currentSprint, Sprint newSprint) {

        if (newSprint.getTeamMood() != 0)
            currentSprint.setTeamMood(newSprint.getTeamMood());
        if (newSprint.getConfidentTarget() != 0)
            currentSprint.setConfidentTarget(newSprint.getConfidentTarget());
        if (newSprint.getTeamMotivation() != 0)
            currentSprint.setTeamMotivation(newSprint.getTeamMotivation());
        if (newSprint.getLastDemoHighlights() != null)
            currentSprint.setLastDemoHighlights(newSprint.getLastDemoHighlights());
        if (newSprint.getMainImpediments() != null)
            currentSprint.setMainImpediments(newSprint.getMainImpediments());
        if (newSprint.getTechnicalDebt() != null)
            currentSprint.setTechnicalDebt(newSprint.getTechnicalDebt());
        if (newSprint.getSecurityDebt() != null)
            currentSprint.setSecurityDebt(newSprint.getSecurityDebt());
        if (newSprint.getDemo() != null) {
            Demo demoUpdated;
            demoUpdated = newSprint.getDemo();
            demoUpdated.setSprint(currentSprint);
            currentSprint.setDemo(demoUpdated);
        }
        return currentSprint;
    }

    public List<Sprint> findAllSprints() {
        return sprintRepository.findAll();
    }
}
