package cockpit.cockpitcore.domaine.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HISTORY_BUG")
public class BugHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    private Date date;

    @Column(name = "ALLBUGSCOUNT")
    private Integer allBugsCount;

    @Column(name = "BUGSCOUNT")
    private Integer bugsCount;

    @Column(name = "TIME_TO_DETECT")
    private double timeToDetect;

    @Column(name = "TIME_TO_FIX")
    private double timeToFix;

    @Column(name = "NB_US_DONE")
    private double nbUSDone;

    @ManyToOne
    @JsonIgnore
    private Mvp mvp;

}
