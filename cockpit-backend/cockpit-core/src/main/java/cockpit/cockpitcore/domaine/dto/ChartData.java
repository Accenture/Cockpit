package cockpit.cockpitcore.domaine.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ChartData {

    private int sprintId;

    private Date closedDate;

    private Long usClosed;

    private Double projectionUsClosed;

    public Integer totalStories;

    public int totalSprints;

    public int expectedUsClosed;

    public int optimisticProjection;

    public int pessimisticProjection;

}
