package com.epam.esm.service.logic_service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.User;
//import com.epam.esm.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserLogicServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserLogicService userLogicService;

    private final User user = new User(1L,"AlexRendal","ffff");

    @Test
    void getById() {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User foundUser = userLogicService.getById(user.getId());
        assertEquals(user,foundUser);
    }

    @Test
    void getAll() {
        Page<User> page = new PageImpl<>(new ArrayList<>(Collections.singletonList(user)));
        Mockito.when(userRepository.findAll(PageRequest.of(1,1))).thenReturn(page);
        Page<User> userList = userLogicService.getAll(1,1);
        assertEquals(1,userList.getTotalElements());
    }
}