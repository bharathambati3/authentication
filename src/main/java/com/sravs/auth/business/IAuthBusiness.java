package com.sravs.auth.business;

import com.sravs.auth.request.LoginRequest;
import com.sravs.auth.response.UserDto;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Local
public interface IAuthBusiness {

    String getLoginPath(HttpServletRequest request);

    UserDto login(HttpServletRequest request, HttpServletResponse response, LoginRequest loginRequest);

    void logout(HttpServletRequest request, HttpServletResponse response, Integer id);

    Boolean isLoggedIn(HttpServletRequest request, Integer id);
}
