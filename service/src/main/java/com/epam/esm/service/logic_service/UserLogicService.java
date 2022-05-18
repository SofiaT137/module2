package com.epam.esm.service.logic_service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionErrorCode.NO_SUCH_ENTITY_CODE;

/**
 * Class UserLogicService is implementation of interface UserService
 * The class presents service logic layer for User entity
 */
@Service("userLogicService")
public class UserLogicService implements UserService<User> {

    private final UserDao userDao;

    private final UserValidator userValidator;

    private static final String NO_USER_WITH_THAT_ID_EXCEPTION = "noUserWithId";

    @Autowired
    public UserLogicService(UserDao userDao, UserValidator userValidator) {
        this.userDao = userDao;
        this.userValidator = userValidator;
    }

    @Override
    public User getById(long id) {
        userValidator.checkID(id);
        Optional<User> receivedOrderById = userDao.getById(id);
        if (!receivedOrderById.isPresent()){
            throw new NoSuchEntityException(NO_USER_WITH_THAT_ID_EXCEPTION,NO_SUCH_ENTITY_CODE);
        }
        return receivedOrderById.get();
    }

    @Override
    public List<User> getAll(int pageSize, int pageNumber) {
        return userDao.getAll(pageSize,pageNumber);
    }

}
