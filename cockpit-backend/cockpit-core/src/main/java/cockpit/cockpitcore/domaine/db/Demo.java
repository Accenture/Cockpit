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
@Table(name = "demo")
public class Demo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATETIME")
    private Date datetime;

    @Column(name = "LOCATION")
    private String location;

    @Column(name= "URLVIDEO")
    public String urlVideo;

    @OneToOne
    @JsonIgnore
    private Sprint sprint;

}
