package com.epam.esm.repository.impl;

import annotations.IT;
import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
class UserCustomRepoImplTest {

    @Resource
    private UserRepository userRepository;

    private static final User user1 = new User();
    private static final User user2 = new User();

    @BeforeEach
    void init() {
        try {
            user1.setLogin("Sofia");
            user1.setPassword("ttt");
            user1.setEnabled(1);
            user1.setRoles(new ArrayList<>());
            user2.setLogin("Anna");
            user2.setPassword("tttt0");
            user2.setEnabled(0);
            user2.setRoles(new ArrayList<>());
            userRepository.save(user1);
            userRepository.save(user2);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void blockUser() {
        userRepository.blockUser(user1);
        Optional<User> blockedFirstUser = userRepository.findUserByLogin(user1.getLogin());
        if (!blockedFirstUser.isPresent()){
            throw new UsernameNotFoundException("Cannot find this user!");
        }
        assertEquals(0,blockedFirstUser.get().getEnabled());
    }

    @Test
    void unblockUser() {
        try {
            userRepository.unblockUser(user2);
            Optional<User> unblockedSecondUser = userRepository.findUserByLogin(user2.getLogin());
            if (!unblockedSecondUser.isPresent()) {
                throw new UsernameNotFoundException("Cannot find this user!");
            }
            assertEquals(1, unblockedSecondUser.get().getEnabled());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}