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
        String password = value.getPassword();
        String login = value.getLogin();
        return new User(login,password);
    }

    @Override
    public UserDto convert(User value) {
        Long id = value.getId();
        String login = value.getLogin();
        return new UserDto(id,login,"");
    }
}

