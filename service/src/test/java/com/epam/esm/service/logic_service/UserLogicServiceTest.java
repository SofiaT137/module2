package com.epam.esm.service.logic_service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserLogicServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserLogicService userLogicService;

    private final User user = new User(1L,"AlexRendal","ffff");

    @Test
    void getById() {
        Mockito.when(userDao.getById(user.getId())).thenReturn(user);
        User foundUser = userLogicService.getById(user.getId());
        assertEquals(user,foundUser);
    }

    @Test
    void getAll() {
        Mockito.when(userDao.findAll()).thenReturn(Collections.singletonList(user));
        Page<User> userList = userLogicService.getAll(1,1);
        assertEquals(1,userList.getTotalElements());
    }
}