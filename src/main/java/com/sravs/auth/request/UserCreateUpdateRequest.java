package com.sravs.auth.request;

public class UserCreateUpdateRequest {

    private String username;
    private String emailId;
    private String password;
    private String mobileNumber;

    public UserCreateUpdateRequest() {
    }

    public String getUsername() {
        return username;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
