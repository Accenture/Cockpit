package cockpit.cockpitcore.domaine.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.io.Serializable;

public class USHistoryCompositeKey implements Serializable {

    @Column(name = "date", nullable = false)
    private String date;

    @ManyToOne
    @JsonIgnore
    private Mvp mvp;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Mvp getMvp() {
        return mvp;
    }

    public void setMvp(Mvp mvp) {
        this.mvp = mvp;
    }

    public USHistoryCompositeKey(String date, Mvp mvp) {
        this.date = date;
        this.mvp = mvp;
    }

    public USHistoryCompositeKey() {
    }
}
