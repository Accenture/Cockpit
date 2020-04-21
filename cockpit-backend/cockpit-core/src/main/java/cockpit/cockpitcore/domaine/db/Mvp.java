package cockpit.cockpitcore.domaine.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mvp")
public class Mvp {

    @Id
    private String id;

    @Size(min = 3, max = 30)
    private String name;

    @Size(max = 400)
    private String pitch;

    private String status;

    private String entity;

    @Column(name = "CURRENTSPRINT")
    @ColumnDefault("0")
    private Integer currentSprint;

    @Column(name = "NBSPRINT")
    @ColumnDefault("0")
    private Integer nbSprint;

    @Temporal(TemporalType.DATE)
    @Column(name = "MVPENDDATE")
    private Date mvpEndDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "MVPSTARTDATE")
    private Date mvpStartDate;

    @Column(name = "MVPAVATARURL")
    private String mvpAvatarUrl;

    @Column(name = "JIRAPROJECTID")
    @ColumnDefault("0")
    @JsonIgnore
    private Integer jiraProjectId;

    @Column(name = "JIRABOARDID")
    @ColumnDefault("0")
    @JsonIgnore
    private Integer jiraBoardId;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "NBUSERSTORIES")
    @ColumnDefault("0")
    private Integer nbUserStories;

    @Column(name = "BUGS_COUNT")
    @ColumnDefault("0")
    private Integer bugsCount;

    @Column(name = "ALL_BUGS_COUNT")
    @ColumnDefault("0")
    private Integer allBugsCount;

    @Column(name = "TIME_TO_FIX")
    @ColumnDefault("-1")
    private BigDecimal timeToFix;

    @Column(name = "TIME_TO_DETECT")
    @ColumnDefault("-1")
    private BigDecimal timeToDetect;

    @OneToOne(mappedBy = "mvp", cascade = CascadeType.ALL, orphanRemoval = true)
    private TechnicalDebt technicalDebt;

    @OneToMany(mappedBy = "mvp", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Sprint> sprints;

    @OneToMany(mappedBy = "mvp", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Collection<UserStory> userStories;

    @OneToMany(mappedBy = "mvp", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Collection<BugHistory> bugHistories;

    @ManyToOne
    private Team team;

    public void assignTeam(Team team) {
        this.team = team;
        if (!team.getMvps().stream().anyMatch(m -> this.id.equals(m.getId()))) {
            team.getMvps().add(this);
        }
    }

    public void removeTeam() {

        if (team.getMvps().stream().anyMatch(m -> this.id.equals(m.getId()))) {
            team.getMvps().remove(this);
        }
        this.team = null;

    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mvp", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Bug> bugs;

    @ManyToMany
    @JoinTable(name = "mvp_technology", joinColumns = {@JoinColumn(name = "mvp_id")}, inverseJoinColumns = {
            @JoinColumn(name = "technology_id")})
    private List<Technology> technologies = new ArrayList<>();

    public Mvp(String id) {
        this.id = id;
    }

    @Column(name = "SharedMVPID")
    private String sharedMVPId;

    @Column(name = "ITERATION_NUMBER")
    @ColumnDefault("0")
    private Integer iterationNumber;

}
