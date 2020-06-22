package com.cockpit.api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraSprintDTO {
    private Integer originBoardId;
    private Date endDate;
    private String name;
    private Integer id;
    private String state;
    private Date startDate;
    private Date completeDate;

    public Integer getOriginBoardId() {
        return originBoardId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }
}
