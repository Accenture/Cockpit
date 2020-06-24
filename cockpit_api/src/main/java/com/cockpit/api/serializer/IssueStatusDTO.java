package com.cockpit.api.serializer;
// Not used but may be useful later

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
