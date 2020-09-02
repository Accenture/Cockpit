package com.cockpit.api.model.dto;

public class UserInfoDTO {

    private String sub;

    private String email;

    private String groups;

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

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }
}
