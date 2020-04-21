package cockpit.mvpinfos.service;

import cockpit.cockpitcore.domaine.db.Technology;
import cockpit.cockpitcore.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnologyService {

    private TechnologyRepository technologyRepository;

    @Autowired
    public TechnologyService(TechnologyRepository technologyRepository) {
     this.technologyRepository = technologyRepository;
    }

    public List<Technology> getAllTechnologies() {
        return technologyRepository.findAll();
    }

    public Technology createTechnology(Technology technology) {
        return technologyRepository.saveAndFlush(technology);
    }

    public boolean isTechnologyAlreadyCreated(Technology technology) {
        Optional<Technology> returnedTechnology = technologyRepository.findById(technology.getName());
        if (!returnedTechnology.isPresent()) return false;
        return true;
    }
}
