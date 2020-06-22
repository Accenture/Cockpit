package com.cockpit.api.model.dto;

public class IssueStatusDTO {
    private Integer id;
    private String name;
    private IssueStatusCategoryDTO statusCategory;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public IssueStatusCategoryDTO getStatusCategory() {
        return statusCategory;
    }
}
