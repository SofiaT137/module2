package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User,UserDto> {

    @Override
    public User convert(UserDto value) {
        String firstName = value.getFirstName();
        String lastName = value.getLastName();
        return new User(firstName,lastName);
    }

    @Override
    public UserDto convert(User value) {
        Long id = value.getId();
        String firstName = value.getFirstName();
        String lastName = value.getLastName();
        return new UserDto(id,firstName,lastName);
    }
}

