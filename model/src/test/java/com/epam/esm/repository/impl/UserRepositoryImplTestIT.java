package com.epam.esm.repository.impl;

import annotations.IT;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
class UserRepositoryImplTestIT {

    @Resource
    private UserRepository userRepository;

    private static final String LOGIN = "Sofiya";

    @BeforeEach
    void init(){
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role("ADMIN"));
        User user = new User(LOGIN,"123");
        user.setRoles(roleList);
        user.setOperation("INSERT");
        user.setTimestamp(LocalDateTime.now());
        User user1 = userRepository.save(user);
    }

    @Test
    void findUserByLogin() {
        Optional<User> user11 = userRepository.findUserByLogin(LOGIN);
        if (!user11.isPresent()){
            throw new UsernameNotFoundException("Cannot find user by it's login!");
        }
        assertEquals(1L,user11.get().getId());
    }

}