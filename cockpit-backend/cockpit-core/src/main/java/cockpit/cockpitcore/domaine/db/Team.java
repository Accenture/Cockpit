package cockpit.cockpitcore.domaine.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTEAM")
    private Integer idTeam;

    @Column(name = "NAME")
    private String name;
    
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "team_member",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<TeamMember> members = new HashSet<>();

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    private List<Mvp> mvps = new ArrayList<>();

    public void addTeamMember(TeamMember teamMember) {
        members.add(teamMember);
        teamMember.getTeams().add(this);
    }

    public void removeTeamMember(TeamMember teamMember) {
        members.remove(teamMember);
        teamMember.getTeams().remove(this);
    }
    
    public void addMvp(Mvp mvp) {
        mvps.add(mvp);
        mvp.assignTeam(this);
    }

    public void removeMvp(Mvp mvp) {
        mvps.remove(mvp);
        mvp.assignTeam(null);
    }

}
