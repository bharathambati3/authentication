package com.sravs.auth.request;

import javax.ws.rs.QueryParam;

public class LoginForm {

    @QueryParam("service")
    private String service;

    @QueryParam("continue")
    private String redirectUrl;

    public LoginForm() {
    }

    public String getService() {
        return service;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
