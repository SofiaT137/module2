package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

/**
 * Class UserConverter presents converter for User and UserDto entities
 */
@Component
public class UserConverter implements Converter<User,UserDto> {

    private static final String PROTECTED_INFO = "Protected info";

    @Override
    public User convert(UserDto value) {
        User user = new User();
        user.setLogin(value.getLogin());
        user.setPassword(value.getPassword());
        int enabled = value.isEnabled() ? 1 : 0;
        user.setEnabled(enabled);
        return user;
    }

    @Override
    public UserDto convert(User value) {
        Long id = value.getId();
        String login = value.getLogin();
        return new UserDto(id,login,PROTECTED_INFO,value.getEnabled() == 1);
    }
}

