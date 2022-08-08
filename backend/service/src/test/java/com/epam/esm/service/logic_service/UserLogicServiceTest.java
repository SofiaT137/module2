package com.epam.esm.service.logic_service;

import com.epam.esm.pagination.Pagination;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserLogicServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Pagination<User> pagination;

    @InjectMocks
    private UserLogicService userLogicService;

    private static User USER;
    private static final String LOGIN = "AlexRendal";
    private static final String PASSWORD = "dddd";
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 1;
    private static final long ID = 1L;

    @BeforeAll
    static void init(){
        USER = new User(ID,LOGIN,PASSWORD);
    }

    @Test
    void getById() {
        Mockito.when(userRepository.findById(ID)).thenReturn(Optional.of(USER));
        User foundUser = userLogicService.getById(USER.getId());
        assertEquals(USER,foundUser);
    }

    @Test
    void getAll() {
        Page<User> page = new PageImpl<>(new ArrayList<>(Collections.singletonList(USER)));
        Mockito.when(pagination.checkHasContent(userRepository.findAll(PageRequest.of(PAGE_NUMBER,PAGE_SIZE))))
                .thenReturn(page);
        Page<User> userList = userLogicService.getAll(PAGE_NUMBER,PAGE_SIZE);
        assertEquals(1,userList.getTotalElements());
    }
}