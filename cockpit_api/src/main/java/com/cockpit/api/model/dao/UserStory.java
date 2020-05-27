package com.cockpit.api.model.dao;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date creationDate;

    private Date startDate;

    private Date doneDate;

    private Integer storyPoint;

    private String description;

    private String issueKey;

    private int jiraIssueId;

    private String priority;

    private String status;

    private String summary;

    @ManyToOne
    private Sprint sprint;

    @ManyToOne
    private Mvp mvp;
}
