package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DevelopmentConfiguration;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevelopmentConfiguration.class)
@ActiveProfiles("dev")
@Sql({
        "classpath:data.sql"
})
class UserDaoImplTest {

    private final UserDao userDao;

    @Autowired
    UserDaoImplTest(UserDao userDao) {
        this.userDao = userDao;
    }

    @Test
    void getById() {
        long id = 1L;
        Optional<User> user = userDao.getById(id);
        if (!user.isPresent()){
            throw new RuntimeException("No such tag!");
        }
        User user1 = user.get();
        assertEquals(id,user1.getId());
    }

    @Test
    void getAll() {
        List<User> allTheUsers = userDao.getAll(3,1);
        assertEquals(2,allTheUsers.size());
    }
}