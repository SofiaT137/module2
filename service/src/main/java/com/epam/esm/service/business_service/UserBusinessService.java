package com.epam.esm.service.business_service;

import com.epam.esm.converter.impl.UserConverter;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.ValidationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class UserBusinessService is implementation of the UserService interface
 * The class presents service business logic for User entity
 */
@Service("userBusinessService")
public class UserBusinessService implements UserService<UserDto> {

    private final UserConverter userConverter;
    private final ValidationFacade validationFacade;
    private UserService<User> userLogicService;

    @Autowired
    public UserBusinessService(UserConverter userConverter,ValidationFacade validationFacade) {
        this.userConverter = userConverter;
        this.validationFacade = validationFacade;
    }

    @Autowired
    @Qualifier("userLogicService")
    public void setUserLogicService(UserService<User> userLogicService) {
        this.userLogicService = userLogicService;
    }

    @Override
    @Transactional
    public UserDto insert(UserDto entity) {
        validationFacade.validate(entity);
        User convertUser = userConverter.convert(entity);
        userLogicService.insert(convertUser);
        return userConverter.convert(convertUser);
    }

    @Override
    public UserDto getById(long id) {
        User user = userLogicService.getById(id);
        return userConverter.convert(user);
    }

    @Override
    public Page<UserDto> getAll(int pageSize, int pageNumber) {
        Page<User> userList = userLogicService.getAll(pageSize, pageNumber);
        return userList.map(userConverter::convert);
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        return userLogicService.loadUserByUsername(username);
    }

    @Override
    public User findUserByUserLogin(String login) {
        return userLogicService.findUserByUserLogin(login);
    }


    @Override
    @Transactional
    public void deleteByID(long id) {
        userLogicService.deleteByID(id);
    }
}
