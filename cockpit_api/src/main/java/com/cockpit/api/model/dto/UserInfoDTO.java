package com.cockpit.api.model.dto;

import java.util.List;

public class UserInfoDTO {

    private String sub;

    private String email;

    private List<String> groups;

    public String getSub() {
        return sub;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
}
