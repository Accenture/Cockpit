package com.cockpit.api.model;

import javax.persistence.*;

@Entity
@Table(name = "sprint")
public class Sprint {
    @Id
    @GeneratedValue(generator = "question_generator")
    @SequenceGenerator(
            name = "question_generator",
            sequenceName = "question_sequence",
            initialValue = 1000
    )
    private Long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String sprintStartDate;
    private String sprintEndDate;
    private String teamMotivation;
    private String teamMood;
    private String teamConfidence;
    private int totalNbUs;
    private int sprintNumber;

    @ManyToOne
    @JoinColumn(name="id_jira", nullable = false)
    private Jira jira;

    public Sprint() {
    }

    public Long getId() {
        return id;
    }

    public String getSprintStartDate() {
        return sprintStartDate;
    }

    public String getSprintEndDate() {
        return sprintEndDate;
    }

    public String getTeamMotivation() {
        return teamMotivation;
    }

    public String getTeamMood() {
        return teamMood;
    }

    public String getTeamConfidence() {
        return teamConfidence;
    }

    public int getTotalNbUs() {
        return totalNbUs;
    }

    public int getSprintNumber() {
        return sprintNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSprintStartDate(String sprintStartDate) {
        this.sprintStartDate = sprintStartDate;
    }

    public void setSprintEndDate(String sprintEndDate) {
        this.sprintEndDate = sprintEndDate;
    }

    public void setTeamMotivation(String teamMotivation) {
        this.teamMotivation = teamMotivation;
    }

    public void setTeamMood(String teamMood) {
        this.teamMood = teamMood;
    }

    public void setTeamConfidence(String teamConfidence) {
        this.teamConfidence = teamConfidence;
    }

    public void setTotalNbUs(int totalNbUs) {
        this.totalNbUs = totalNbUs;
    }

    public void setSprintNumber(int sprintNumber) {
        this.sprintNumber = sprintNumber;
    }
}
