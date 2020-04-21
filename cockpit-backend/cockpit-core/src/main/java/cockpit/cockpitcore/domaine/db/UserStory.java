package cockpit.cockpitcore.domaine.db;

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
@Table(name = "userstory")
public class UserStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column(name = "JIRAISSUEID")
    private int jiraIssueId;

    @ManyToOne
    private Sprint sprint;

    @Column(name = "ISSUEKEY")
    private String issueKey;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SUMMARY")
    private String summary;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ESTIMATION")
    private int estimation;

    @Temporal(TemporalType.DATE)
    @Column(name = "DONEDATE")
    private Date doneDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "STARTDATE")
    private Date startDate;

    @Column(name = "PRIORITY")
    private String priority;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATEDDATE")
    private Date createdDate;

    @ManyToOne
    private Mvp mvp;
}
