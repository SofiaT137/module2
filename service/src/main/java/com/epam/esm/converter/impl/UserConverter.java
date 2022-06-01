package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Class UserConverter presents converter for User and UserDto entities
 */
@Component
public class UserConverter implements Converter<User,UserDto> {

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
        return new UserDto(id,login,"Protected info",value.getEnabled() == 1);
    }
}

