package cockpit.cockpitcore.domaine.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "technology")
public class Technology {

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "LOGOURL")
    private String logoUrl;

    @ManyToMany(mappedBy = "technologies")
    @JsonIgnore
    private List<Mvp> mvps = new ArrayList<>();

}