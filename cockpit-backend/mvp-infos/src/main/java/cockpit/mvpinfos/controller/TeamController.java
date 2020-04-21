package cockpit.mvpinfos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cockpit.cockpitcore.domaine.db.Team;
import cockpit.cockpitcore.domaine.db.TeamMember;
import cockpit.mvpinfos.exception.ResourceNotFoundException;
import cockpit.mvpinfos.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/1.0/team")
@Api(value = "/api/1.0/team")
@CrossOrigin
@Slf4j
public class TeamController {

    private TeamService teamService;
    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @ApiOperation(value = "Get all teams")
    @RequestMapping(value = "/all", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Team> getTeams() { return teamService.getAllTeams(); }


    @ApiOperation(value = "Get all team members in the Database")
    @RequestMapping(value = "/members/all", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Set<TeamMember> getAllMembers() {
        Set<TeamMember> allMembers = new HashSet<>();

        try {
            allMembers = teamService.getAllTeamMembersOrderAlph();
            return  allMembers;
        }
        catch (ResourceNotFoundException e) {
            log.error("getAllMembers : Resource not found", e.getMessage());
        }
        return allMembers;
    }


    @ApiOperation(value = "Get team by Name")
    @RequestMapping(value = "/name/{team}", method = GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public Team getTeamByName(@PathVariable("team") String team) {
        try{
            return teamService.getTeamByName(team);
        }
        catch (ResourceNotFoundException e) {
            log.error("getOneTeamByName : Resource not found", e);
        }
        return new Team();
    }
    
    
    @ApiOperation("Update team members of a team")
    @RequestMapping(value = "/{id}/updateTeamMembers", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateTeamMembers(@PathVariable("id") Integer id, @RequestBody Set<TeamMember> teamMembers) {

        log.info("updateTeam - updating team members of the team {%s} with {%s}", id, teamMembers);
        String result;
        try {
            result = teamService.updateTeamMembers(id,teamMembers);
        } catch (ResourceNotFoundException e) {
          log.error(e.getMessage());
          return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @ApiOperation("Update team information")
    @RequestMapping(value = "/{id}/updateInfo", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateTeamInformation(@PathVariable("id") String id, @RequestBody Team team) {

        Team foundTeam = null;
        try {
            foundTeam = teamService.getTeamById(Integer.parseInt(id));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        if (foundTeam != null) {
            log.info("updateTeam - updating team information of the team [%s] with [%s]", id, team);
            team.setIdTeam(Integer.parseInt(id));
            try {
                team = teamService.updateTeamInformation(team);
            } catch (ResourceNotFoundException e) {
                log.error(e.getMessage());
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Team>(team, HttpStatus.OK);
    }

    @ApiOperation("Create new Team in DB")
    @RequestMapping(value = "/creation", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createNewTeam(@RequestBody Team newTeam) {
        if(!newTeam.getName().equals("")) {
            Team teamCreated = teamService.saveNewTeam(newTeam);
            return new ResponseEntity<Team>(teamCreated, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Team name not defined",HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation("Update given team with removing members")
    @PutMapping(value = "removeMember/{teamId}")
    public ResponseEntity<?> deleteMemberFromTeam(@PathVariable("teamId") Integer teamId, @RequestBody TeamMember memberTodelete) {
        Team teamToUpdate = null;
        try {
            teamToUpdate = teamService.removeMemberFromTeam(memberTodelete,teamId);
            return new ResponseEntity<Team>(teamToUpdate,HttpStatus.NO_CONTENT);
        }
        catch (ResourceNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
