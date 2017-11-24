package com.sravs.auth.business;

import com.sravs.auth.request.UserCreateUpdateRequest;
import com.sravs.auth.response.UserDto;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IUserBusiness {

    UserDto create(UserCreateUpdateRequest request);

    UserDto find(Integer id);

    void delete(Integer id);

    UserDto update(Integer id, UserCreateUpdateRequest request);

    List<UserDto> findAll();
}
