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
@Table(name = "bug")
public class Bug {
    @Id
    @Column(name = "ISSUE_KEY")
    private String issueKey;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SUMMARY")
    private String summary;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "RESOLUTION_DATE")
    private Date resolutionDate;

    @Column(name = "Related_US")
    private String relatedUserStory;

    @Column(name = "STAGE")
    private String stage;

    @Column(name = "PRIORITY")
    private String priority;

    @Column(name = "ENV_DETECTION")
    private String envDetection;

    @Column(name = "SEVERITY")
    private String severity;

    @ManyToOne
    @JsonIgnore
    private Mvp mvp;
}