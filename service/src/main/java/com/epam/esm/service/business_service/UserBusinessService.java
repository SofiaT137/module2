package com.epam.esm.service.business_service;

import com.epam.esm.converter.impl.UserConverter;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class UserBusinessService is implementation of the UserService interface
 * The class presents service business logic for User entity
 */
@Service("userBusinessService")
public class UserBusinessService implements UserService<UserDto> {

    private final UserConverter userConverter;
    private UserService<User> userLogicService;

    @Autowired
    public UserBusinessService(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Autowired
    @Qualifier("userLogicService")
    public void setUserLogicService(UserService<User> userLogicService) {
        this.userLogicService = userLogicService;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    public UserDto getById(long id) {
        User user = userLogicService.getById(id);
        return userConverter.convert(user);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    public List<UserDto> getAll(int pageSize, int pageNumber) {
        List<User> userList = userLogicService.getAll(pageSize, pageNumber);
        return userList.stream().map(userConverter::convert).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userLogicService.loadUserByUsername(username);
    }
}
