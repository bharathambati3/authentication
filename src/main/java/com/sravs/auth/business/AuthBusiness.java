package com.sravs.auth.business;

import com.sravs.auth.db.domain.User;
import com.sravs.auth.request.LoginRequest;
import com.sravs.auth.response.UserDto;
import com.sravs.auth.db.dao.UserDao;
import com.sravs.auth.util.BCrypt;
import com.sravs.auth.util.AuthUtil;
import com.sravs.auth.util.Converter;
import com.sravs.common.db.Model;
import com.sravs.common.exception.AuthApplicationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Stateless
public class AuthBusiness implements IAuthBusiness {

    @Inject @Model
    UserDao userDao;

    @Inject
    AuthUtil authUtil;

    @Override
    public String getLoginPath(HttpServletRequest request) {
        return authUtil.constructFullPath(request, "/login.html");
    }

    @Override
    public UserDto login(HttpServletRequest request, HttpServletResponse response,
                         LoginRequest loginRequest) {

        authUtil.invalidateSession(request, response);
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        boolean registeredUser = false;
        User user = null;
        if (username != null && password != null) {
            try{
                 user = userDao.getPassword(username);
                String storedPassword = user.getPassword();
                registeredUser = BCrypt.checkpw(password, storedPassword);
            }catch (NoResultException e){
                registeredUser = false;
            }
        }

        if (!registeredUser) {
            throw new AuthApplicationException("Not a valid login. try again");
        } else {

            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            return Converter.convert(user);
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Integer id) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Integer userId = (Integer) session.getAttribute("userId");
            if (Objects.equals(id, userId)) {
                authUtil.invalidateSession(request, response);
            } else {
                throw new AuthApplicationException("Hack alert");
            }
        }
        throw new AuthApplicationException("User is not logged in").setData(id);
    }

    @Override
    public Boolean isLoggedIn(HttpServletRequest request, Integer id) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Integer userId = (Integer) session.getAttribute("userId");
            return Objects.equals(userId, id);
        } else {
            return false;
        }
    }
}
