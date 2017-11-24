package com.sravs.auth.util;

import com.sravs.auth.db.domain.User;
import com.sravs.auth.request.UserCreateUpdateRequest;
import com.sravs.auth.response.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class Converter {

    public static User convert(UserCreateUpdateRequest request) {
        User user = new User();
        user.setPassword(request.getPassword());
        user.setEmailId(request.getEmailId());
        user.setUsername(request.getUsername());
        user.setMobileNumber(request.getMobileNumber());
        return user;
    }

    public static UserDto convert(User user) {
        if (user == null) {
            return null;
        }
        UserDto response = new UserDto();
        response.setId(user.getId());
        response.setEmailId(user.getEmailId());
        response.setUsername(user.getUsername());
        response.setMobileNumber(user.getMobileNumber());
        return response;
    }

    public static List<UserDto> convert(List<User> users) {
        return users.stream().map(Converter::convert).collect(Collectors.toList());
    }
}
