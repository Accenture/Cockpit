package com.cockpit.api.model;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Technology{
    @Id
    @GeneratedValue(generator = "question_generator")
    @SequenceGenerator(
            name = "question_generator",
            sequenceName = "question_sequence",
            initialValue = 1000
    )
    private Long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    private String url;
    @ManyToMany
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
