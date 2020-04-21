package cockpit.cockpitcore.domaine.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TEAMMEMBER")
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDMEMBER")
    private Integer idMember;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "FIRSTNAME")
    private  String firstName;

    @Column(name = "EMAILID")
    private String emailId;

    @Column(name = "AVATARURL")
    private String avatarUrl;

    @Column(name = "IDROLE")
    private String idRole;

    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    private Set<Team> teams = new HashSet<>();

}
