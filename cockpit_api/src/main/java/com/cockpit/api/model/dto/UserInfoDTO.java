package com.cockpit.api.model.dto;

public class UserInfoDTO {

    private String sub;

    private String email;

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
}
