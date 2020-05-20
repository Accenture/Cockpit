package cockpit.cockpitcore.domaine.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sprint")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column(name = "SPRINTNUMBER")
    private int sprintNumber;

    @ManyToOne
    @JsonIgnore
    private Mvp mvp;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL,  orphanRemoval=true)
    @JsonIgnore
    private List<UserStory> userStories;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval=true)
    @JsonIgnore
    private List<UserStoriesHistory> userStoriesHistory;

    @Temporal(TemporalType.DATE)
    @Column(name = "SPRINTENDDATE")
    private Date sprintEndDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "SPRINTSTARTDATE")
    private Date sprintStartDate;

    @Column(name = "JIRASPRINTID")
    @JsonIgnore
    private int jiraSprintId;

    @Column(name = "LASTDEMOHIGHLIGHTS",columnDefinition="VARCHAR(1000)")
    private String lastDemoHighlights;

    @Column(name = "MAINIMPEDIMENTS", columnDefinition="VARCHAR(1000)")
    private String mainImpediments;

    @Column(name = "TECHNICALDEBT")
    private String technicalDebt;

    @Column(name = "SECURITYDEBT")
    private String securityDebt;

    @OneToOne(mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval=true)
    private Demo demo;

    @Column(name = "TEAMMOOD")
    @Range(min = 0, max = 4)
    private int teamMood;

    @Column(name = "TEAMMOTIVATION")
    @Range(min = 0, max = 4)
    private int teamMotivation;

    @Column(name = "CONFIDENTTARGET")
    @Range(min = 0, max = 4)
    private int confidentTarget;

    @ColumnDefault("0")
    @Column(name = "TechnicalDebtKPI")
    private double technicalDebtKPI;

    @Column(name = "totalNbUS")
    private Integer totalNbUs;

}
