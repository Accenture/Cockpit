package cockpit.mvpinfos.service;

import cockpit.cockpitcore.domaine.db.Team;
import cockpit.cockpitcore.domaine.db.TeamMember;
import cockpit.mvpinfos.exception.ResourceNotFoundException;
import cockpit.cockpitcore.repository.TeamMemberRepository;
import cockpit.cockpitcore.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class TeamService {

    private TeamRepository teamRepository;
    private TeamMemberRepository teamMemberRepository;
    @Autowired
    public TeamService(TeamRepository teamRepository, TeamMemberRepository teamMemberRepository) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    public Team getTeamByName(String name) throws ResourceNotFoundException {
        Team team;
        team = teamRepository.findByName(name);
        if(team == null){
            throw new ResourceNotFoundException("getTeamByName : Team Repository returned null");
        }
        return team;
    }

    public Team saveNewTeam(Team team)  {
        return  teamRepository.save(team);
    }
    
    /**
     * Update the name of the given team in the database
     * @param team
     * @return
     */
    public Team updateTeamInformation(Team team) throws ResourceNotFoundException {

    	Team teamToBeUpdated = teamRepository.getOne(team.getIdTeam());

    	if (null == teamToBeUpdated) {
    		throw new ResourceNotFoundException("Team not found with Id: "+team.getIdTeam());
    	}
    	teamToBeUpdated.setName(team.getName());
    	return teamRepository.save(teamToBeUpdated);
    }

    public Team getTeamById(Integer id) throws ResourceNotFoundException {
        Team team = teamRepository.getOne(id);;
        if(team == null) {
            throw new ResourceNotFoundException("Team with ID : " + id + " not found");
        }
        return team;
    }

    /**
     * Update the team members of the given team in database based on the list of team members passed in the parameter.
     * @param idTeam
     * @return
     * @throws ResourceNotFoundException 
     */
    public String updateTeamMembers(Integer idTeam, Set<TeamMember> members) throws ResourceNotFoundException {

        String result = null;
        Team teamToUpdate = teamRepository.getOne(idTeam);
        if (null == teamToUpdate) {
            throw new ResourceNotFoundException("Team not found" );
        }

        for(TeamMember tm : members) {
            // The member is new
            if(tm.getIdMember() <= -1) {
                tm.setIdMember(null);
                teamToUpdate.getMembers().add(tm);
                teamRepository.save(teamToUpdate);
                log.info("Team Member with name : "+ tm.getLastName() + " has been created");
                result = "Member with name "+ tm.getLastName() + " has been added in Team";
            }
            //Else the member is not new, we should retrieve it and update its information
            else {
                TeamMember existingMember = teamMemberRepository.getOne(tm.getIdMember());
                existingMember.setAvatarUrl(tm.getAvatarUrl());
                existingMember.setEmailId(tm.getEmailId());
                existingMember.setFirstName(tm.getFirstName());
                existingMember.setLastName(tm.getLastName());
                existingMember.setIdRole(tm.getIdRole());
                teamMemberRepository.save(existingMember);
                log.info("Team Member with name : "+ tm.getLastName() + " has been updated");
                if(result != null) {
                    result = result + "\n" + "Member with name " + tm.getLastName() + "has been updated";
                }
                else {
                    result = "Member with name "+ tm.getLastName() + " has been added in Team";
                }
            }
        }

        return result;
    }

    public List<Team> getAllTeams() {
        List<Team> teamList;
        teamList = teamRepository.findAll();
        return teamList;
    }

    public Team removeMemberFromTeam(TeamMember member, Integer idTeam) throws ResourceNotFoundException {
        Team existingTeam;
        Team teamUpdated;
        existingTeam = teamRepository.getOne(idTeam);

        if(existingTeam == null) {
            throw new ResourceNotFoundException("Team not found in DB");
        }
        else {
            TeamMember memberToRemove = teamMemberRepository.getOne(member.getIdMember());
            existingTeam.removeTeamMember(memberToRemove);
            teamUpdated = teamRepository.save(existingTeam);
        }
        return teamUpdated;
    }

    public void deleteTeam(Team team) {
        teamRepository.delete(team);
    }

    public Set<TeamMember> getAllTeamMembersOrderAlph() throws ResourceNotFoundException {
        Set<TeamMember> memberList;

        memberList = teamMemberRepository.findAllByOrderByLastName();
        if(memberList == null) {
            throw new ResourceNotFoundException("Error occurs while trying to get all members");
        }
        return memberList;
    }

}
