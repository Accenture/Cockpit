package com.cockpit.api.serializer;

import com.cockpit.api.serializer.IssueDTO;

import java.util.ArrayList;
// Not used but may be useful later

public class JiraProjectIssueDTO {
    private Integer total;
    private ArrayList<IssueDTO> issues;

    public Integer getTotal() {
        return total;
    }

    public ArrayList<IssueDTO> getIssues() {
        return issues;
    }
}
