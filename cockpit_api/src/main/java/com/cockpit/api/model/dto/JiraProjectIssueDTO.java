package com.cockpit.api.model.dto;

import org.json.JSONArray;

import java.util.ArrayList;

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
