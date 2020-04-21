package cockpit.cockpitcore.domaine.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userstorieshistory")
public class UserStoriesHistory {


    @EmbeddedId
    private USHistoryCompositeKey usHistoryCompositeKey;

    @Column(name = "NBUSERSTORIESCREATED")
    @ColumnDefault("0")
    private int nbUserStoriesCreated;

    @Column(name = "NBUSERSTORIESDONE")
    @ColumnDefault("0")
    private int nbUserStoriesDone;

    @Column(name = "TOTALNBUSERSTORIES")
    @ColumnDefault("0")
    private int totalNbStories;

    @ManyToOne
    private Sprint sprint;

    public UserStoriesHistory(UserStory o) {
        this.usHistoryCompositeKey.setDate(o.getCreatedDate().toString());
    }

}
