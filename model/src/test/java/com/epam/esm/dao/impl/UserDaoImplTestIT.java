package com.epam.esm.dao.impl;

import annotations.IT;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
class UserDaoImplTestIT {

//    private final UserDao userDao;
//    private static final String NO_SUCH_ELEMENT_EXCEPTION = "No such tag!";
//    private static final Long ID = 1L;
//    private static final Integer PAGE_NUMBER = 1;
//    private static final Integer PAGE_SIZE = 3;
//    private static final Integer USERS_LIST_SIZE = 3;
//
//    @Autowired
//    UserDaoImplTestIT(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    @Test
//    void getById() {
//        Optional<User> user = userDao.getById(ID);
//        if (!user.isPresent()){
//            throw new NoSuchElementException(NO_SUCH_ELEMENT_EXCEPTION);
//        }
//        User foundedUser = user.get();
//        assertEquals(ID,foundedUser.getId());
//    }
//
//    @Test
//    void getAll() {
//        List<User> allTheUsers = userDao.getAll(PAGE_SIZE,PAGE_NUMBER);
//        assertEquals(USERS_LIST_SIZE,allTheUsers.size());
//    }
}