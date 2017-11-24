package com.sravs.auth.service;

import com.sravs.auth.business.IAuthBusiness;
import com.sravs.auth.business.IUserBusiness;
import com.sravs.auth.request.LoginRequest;
import com.sravs.auth.request.UserCreateUpdateRequest;
import com.sravs.auth.response.UserDto;
import com.sravs.common.response.BaseResponse;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class AuthService {

    @Inject
    IUserBusiness userBusiness;

    @Inject
    IAuthBusiness authBusiness;

    //--login rest.
    @GET
    @Path("/user/login/info")
    @Produces(MediaType.APPLICATION_JSON)
    public BaseResponse getUserLoginInfo(@Context HttpServletRequest request) {
        String fullUrl = authBusiness.getLoginPath(request);
        return BaseResponse.build(fullUrl);
    }

    @POST
    @Path("/user/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BaseResponse login(@Context HttpServletRequest request,
                              @Context HttpServletResponse response, LoginRequest loginRequest) {
        UserDto userDto = authBusiness.login(request, response, loginRequest);
        return BaseResponse.build(userDto);
    }

    @DELETE
    @Path("/user/{id}/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public BaseResponse logout(@Context HttpServletRequest request, @Context HttpServletResponse response,
                               @PathParam("id") Integer id) {
        authBusiness.logout(request, response, id);
        return BaseResponse.build(null);
    }

    @GET
    @Path("/user/{id}/isLoggedIn")
    @Produces(MediaType.APPLICATION_JSON)
    public BaseResponse isLoggedIn(@Context HttpServletRequest request, @PathParam("id") Integer id) {
        Boolean loggedIn = authBusiness.isLoggedIn(request, id);
        return BaseResponse.build(loggedIn);
    }

    //--user rest.
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user")
    public BaseResponse createUser(UserCreateUpdateRequest userCreateRequest) {
        UserDto response = userBusiness.create(userCreateRequest);
        return BaseResponse.build(response);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{id}")
    public BaseResponse findUser(@PathParam("id") Integer id) {
        UserDto response = userBusiness.find(id);
        return BaseResponse.build(response);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{id}")
    public BaseResponse updateUser(@PathParam("id") Integer id, UserCreateUpdateRequest userCreateRequest) {
        UserDto update = userBusiness.update(id, userCreateRequest);
        return BaseResponse.build(update);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{id}")
    public BaseResponse deleteUser(@PathParam("id") Integer id) {
        userBusiness.delete(id);
        return BaseResponse.build(id);
    }

    @GET
    @Path("users")
    @Produces(MediaType.APPLICATION_JSON)
    public BaseResponse users() {
        List<UserDto> all = userBusiness.findAll();
        return BaseResponse.build(all);
    }

}