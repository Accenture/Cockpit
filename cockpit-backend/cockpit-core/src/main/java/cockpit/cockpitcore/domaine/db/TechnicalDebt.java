package cockpit.cockpitcore.domaine.db;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TECHNICAL_DEBT")
public class TechnicalDebt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "VALUE")
    private Integer value;

    @OneToOne
    @JsonIgnore
    private Mvp mvp;
}
