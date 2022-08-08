package com.epam.esm.service.business_service;

import com.epam.esm.converter.impl.UserConverter;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.ValidationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
    public Page<UserDto> getAll(int pageNumber, int pageSize) {
        Page<User> userList = userLogicService.getAll(pageNumber, pageSize);
        return userList.map(userConverter::convert);
    }

    @Override
    public User findUserByUserLogin(String login) {
        return userLogicService.findUserByUserLogin(login);
    }

    @Override
    public User blockUser(String login) {
       return userLogicService.blockUser(login);
    }

    @Override
    public User unblockUser(String login) {
        return userLogicService.unblockUser(login);
    }

}
