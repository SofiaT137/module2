package com.epam.esm.repository.impl;

import annotations.IT;
import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
class UserCustomRepoImplTest {

    @Resource
    private UserRepository userRepository;

    private static final User user1 = new User();
    private static final User user2 = new User();
    private static final String LOGIN_1 = "Sofia";
    private static final String LOGIN_2 = "Anna";
    private static final String PASSWORD = "ttt";
    private static final String PASSWORD_2 = "tttt";
    private static final String CANNOT_FIND_THE_USER = "Cannot find this user!";
    private static final int ENABLED = 1;
    private static final int DISABLED = 0;

    @BeforeAll
     static void init() {
        user1.setLogin(LOGIN_1);
        user1.setPassword(PASSWORD);
        user1.setEnabled(ENABLED);
        user1.setRoles(new ArrayList<>());
        user2.setLogin(LOGIN_2);
        user2.setPassword(PASSWORD_2);
        user2.setEnabled(DISABLED);
        user2.setRoles(new ArrayList<>());
    }

    @Test
    void blockUser() {
        userRepository.save(user1);
        userRepository.blockUser(user1);
        Optional<User> blockedFirstUser = userRepository.findUserByLogin(user1.getLogin());
        if (!blockedFirstUser.isPresent()){
            throw new UsernameNotFoundException(CANNOT_FIND_THE_USER);
        }
        assertEquals(DISABLED,blockedFirstUser.get().getEnabled());
    }


    @Test
    void unblockUser() {
        userRepository.save(user2);
        userRepository.unblockUser(user2);
        Optional<User> unblockedSecondUser = userRepository.findUserByLogin(user2.getLogin());
        if (!unblockedSecondUser.isPresent()) {
            throw new UsernameNotFoundException(CANNOT_FIND_THE_USER);
        }
        assertEquals(ENABLED, unblockedSecondUser.get().getEnabled());
    }


}