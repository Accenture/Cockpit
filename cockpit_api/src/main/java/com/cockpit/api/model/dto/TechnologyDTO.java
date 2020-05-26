package com.cockpit.api.model.dto;

import com.cockpit.api.model.dao.Mvp;
import java.util.Set;

public class TechnologyDTO {
    private Long id;

    private String name;

    private String url;

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
