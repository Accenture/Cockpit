package com.cockpit.api.model.dto;

import java.util.Date;

public class IssueFieldsDTO {
    private IssueTypeDTO issueType;
    private Date resolutionDate;
    private Date created;
    private IssuePriorityDTO priority;
    private IssueStatusDTO status;
}
