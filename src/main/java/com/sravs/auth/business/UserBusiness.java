package com.sravs.auth.business;

import com.sravs.auth.db.dao.UserDao;
import com.sravs.auth.db.domain.User;
import com.sravs.auth.request.UserCreateUpdateRequest;
import com.sravs.auth.response.UserDto;
import com.sravs.auth.util.BCrypt;
import com.sravs.auth.util.Converter;
import com.sravs.common.db.Model;
import com.sravs.common.exception.ApplicationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
public class UserBusiness implements IUserBusiness {

    @Inject @Model
    UserDao userDao;

    public UserBusiness() {
        System.out.println("Created new UserBusiness " + this);
    }

    @Override
    public UserDto create(UserCreateUpdateRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();
        if (username == null || password == null) {
            throw new ApplicationException("Invalid username password").setStatus(Response.Status.BAD_REQUEST);
        }

        if (username.length() < 6) {
            throw new ApplicationException("Minimum username length is 6").setStatus(Response.Status.BAD_REQUEST);
        }

        if (password.length() < 6) {
            throw new ApplicationException("Minimum password length is 6").setStatus(Response.Status.BAD_REQUEST);
        }

        String salt = BCrypt.gensalt();
        String key = BCrypt.hashpw(password, salt);

        request.setPassword(key);
        User user = Converter.convert(request);
        user = userDao.create(user);
        return Converter.convert(user);
    }

    @Override
    public UserDto find(Integer id) {
        User user = getUserForId(id);
        return Converter.convert(user);
    }


    @Override
    public void delete(Integer id) {
        User user = getUserForId(id);
        userDao.delete(user);
    }

    @Override
    public UserDto update(Integer id, UserCreateUpdateRequest request) {
        getUserForId(id);
        User user = Converter.convert(request);
        user.setId(id);
        userDao.update(user);
        return Converter.convert(user);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userDao.findAll();
        return Converter.convert(users);
    }
    private User getUserForId(Integer id) {
        User user = userDao.find(id);
        if (user == null) {
            throw new ApplicationException("No user found for given id").setData(id);
        }
        return user;
    }
}
