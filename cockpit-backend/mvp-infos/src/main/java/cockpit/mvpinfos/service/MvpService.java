package cockpit.mvpinfos.service;

import cockpit.cockpitcore.domaine.db.Mvp;
import cockpit.cockpitcore.domaine.db.Team;
import cockpit.cockpitcore.domaine.db.Technology;
import cockpit.mvpinfos.exception.ResourceNotFoundException;
import cockpit.cockpitcore.repository.MvpRepository;
import cockpit.cockpitcore.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MvpService {

    private MvpRepository mvpRepository;
    private TeamRepository teamRepository;
    private TechnologyService technologyService;

    @Autowired
    public MvpService(MvpRepository mvpRepository, TeamRepository teamRepository, TechnologyService technologyService) {
        this.mvpRepository = mvpRepository;
        this.teamRepository = teamRepository;
        this.technologyService = technologyService;
    }
    /**
     * Assign the selected team to the given Mvp
     *
     * @param mvpId
     * @param teamId
     * @return
     * @throws ResourceNotFoundException
     */
    public Mvp assignTeamOfMvp(String mvpId, Integer teamId) throws ResourceNotFoundException {

        Mvp mvp = mvpRepository.getOne(mvpId);
        if (null == mvp) {
            throw new ResourceNotFoundException("Mvp not found with Id: " + teamId);
        }
        Team team = teamRepository.getOne(teamId);
        if (null == team) {
            throw new ResourceNotFoundException("Team not found with Name: " + teamId);
        }
        team.addMvp(mvp);
        return mvpRepository.save(mvp);

    }

    /**
     * Unassign the team of the given Mvp
     *
     * @param mvpId
     * @return
     * @throws ResourceNotFoundException
     */
    public Mvp unassignTeamOfMvp(String mvpId) throws ResourceNotFoundException {

        Mvp mvp = mvpRepository.getOne(mvpId);
        if (null == mvp) {
            throw new ResourceNotFoundException("Mvp not found with Id: " + mvpId);
        }
        mvp.removeTeam();
        return mvpRepository.save(mvp);

    }

    public List<Mvp> getAllMvpByOrderByMvpStartDateDesc() throws ResourceNotFoundException {
        List<Mvp> mvpList;
        mvpList = mvpRepository.findAllByOrderByMvpStartDateDesc();
        if (mvpList == null) {
            throw new ResourceNotFoundException("getAllMvpByOrderByMvpStartDateDesc: MVP Repository returned null");
        }
        return mvpList.stream().filter(mvp -> mvp.getJiraProjectId() != 0).collect(Collectors.toList());
    }

    public List<Object[]> getAllLightMVPs() throws ResourceNotFoundException {
        List<Object[]> mvpList;
        mvpList = mvpRepository.findAllLightMVPs();
        if (mvpList == null) {
            throw new ResourceNotFoundException("getAllMvpByOrderByMvpStartDateDesc: MVP Repository returned null");
        }
        return mvpList;
    }

    public Mvp getById(String id) throws ResourceNotFoundException {
        Optional<Mvp> optional = mvpRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("getById: [UserStories Repository]the returned Optional<Mvp> object do"
                    + " not have a value");
        }
        return optional.get();
    }

    public Mvp updateMvp(Mvp mvp) {
        return mvpRepository.save(mvp);
    }

    public Mvp updateMvpInfo(Mvp foundMvp, Mvp newMvp) {

        if (newMvp.getPitch() != null) {
            foundMvp.setPitch(newMvp.getPitch());
        }
        if (newMvp.getName() != null) {
            foundMvp.setName(newMvp.getName());
        }
        if (newMvp.getStatus() != null) {
            foundMvp.setStatus(newMvp.getStatus());
        }
        if(newMvp.getCurrentSprint() != null) {
            foundMvp.setCurrentSprint(newMvp.getCurrentSprint());
        }
        if(newMvp.getJiraProjectId() != null) {
            foundMvp.setJiraProjectId(newMvp.getJiraProjectId());
        }
        if (newMvp.getEntity() != null) {
            foundMvp.setEntity(newMvp.getEntity());
        }
        if (newMvp.getLocation() != null) {
            foundMvp.setLocation(newMvp.getLocation());
        }
        if (newMvp.getNbSprint() != 0) {
            foundMvp.setNbSprint(newMvp.getNbSprint());
        }
        if (newMvp.getMvpAvatarUrl() != null) {
            foundMvp.setMvpAvatarUrl(newMvp.getMvpAvatarUrl());
        }
        if (newMvp.getMvpStartDate() != null) {
            foundMvp.setMvpStartDate(newMvp.getMvpStartDate());
        }
        if (newMvp.getMvpEndDate() != null) {
            foundMvp.setMvpEndDate(newMvp.getMvpEndDate());
        }
        if (newMvp.getSharedMVPId() != null) {
            foundMvp.setSharedMVPId(newMvp.getSharedMVPId());
        }
        if (newMvp.getIterationNumber() != null) {
            foundMvp.setIterationNumber(newMvp.getIterationNumber());
        }
        if (newMvp.getTechnologies() != null && !newMvp.getTechnologies().isEmpty()) {
            for (Technology technology : newMvp.getTechnologies()) {
                if (!technologyService.isTechnologyAlreadyCreated(technology)) {
                    technologyService.createTechnology(technology);
                }
            }
            foundMvp.setTechnologies(newMvp.getTechnologies());
        }
        return foundMvp;
    }
    //FIXME: To be detailed for initializing a new MVP
    public Mvp initializeANewMvp(Mvp nonExistingMvp) {
        nonExistingMvp.setNbSprint(8);
        nonExistingMvp.setBugsCount(0);
        nonExistingMvp.setAllBugsCount(0);
        return nonExistingMvp;
    }

    public String deleteMvp(Mvp mvpToDelete) throws ResourceNotFoundException {
        String result = "";
        String name = mvpToDelete.getName();

        try {

            mvpRepository.delete(mvpToDelete);
            result = name.concat(" has been deleted");
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error occurred when trying to delete member");
        }
        return result;
    }
}
