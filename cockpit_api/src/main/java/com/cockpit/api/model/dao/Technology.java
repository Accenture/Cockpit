package com.cockpit.api.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Technology{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    @ManyToMany
    @JsonIgnore
    Set<Mvp> mvps;

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMvps(Set<Mvp> mvps) {
        this.mvps = mvps;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Set<Mvp> getMvps() {
        return mvps;
    }
}
