package com.epam.esm.service.logic_service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionErrorCode.NO_SUCH_ENTITY_CODE;

@Service("userLogicService")
public class UserLogicService implements UserService<User> {

    private final UserDao userDao;

    @Autowired
    public UserLogicService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getById(long id) {
        Optional<User> receivedOrderById = userDao.getById(id);
        if (!receivedOrderById.isPresent()){
            throw new NoSuchEntityException("No user with that id!",NO_SUCH_ENTITY_CODE);
        }
        return receivedOrderById.get();
    }

    @Override
    public List<User> getAll(int pageSize, int pageNumber) {
        return userDao.getAll(pageSize,pageNumber);
    }
}
