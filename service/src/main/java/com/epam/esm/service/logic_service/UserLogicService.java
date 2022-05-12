package com.epam.esm.service.logic_service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionErrorCode.CANNOT_INSERT_ENTITY_CODE;
import static com.epam.esm.exceptions.ExceptionErrorCode.NO_SUCH_ENTITY_CODE;

@Service("userLogicService")
public class UserLogicService implements UserService<User> {

    private final UserDao userDao;

    private static final String NO_USER_WITH_THAT_ID_EXCEPTION = "noUserWithId";

    @Autowired
    public UserLogicService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getById(long id) {
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

    @Override
    @Transactional
    public User insert(User entity) {
        Optional<User> insertedUser = userDao.insert(entity);
        if (!insertedUser.isPresent()){
            throw new CannotInsertEntityException("CANNOT_INSERT_THIS_TAG_MESSAGE",CANNOT_INSERT_ENTITY_CODE);
        }
        return insertedUser.get();
    }
}
