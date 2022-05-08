package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DevelopmentConfiguration;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevelopmentConfiguration.class)
@ActiveProfiles("dev")
@Sql({
        "classpath:data.sql"
})
class OrderDaoImplTest {

    private final OrderDao orderDao;
    private final UserDao userDao;

    @Autowired
    OrderDaoImplTest(OrderDao orderDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
    }

    @Test
    @Transactional
    void insert() {
        Optional<User> userDaoOptional = userDao.getById(2L);
        if (!userDaoOptional.isPresent()){
            throw new RuntimeException("Cannot find user!");
        }
        User user = userDaoOptional.get();
        orderDao.insert(new Order(500.18, LocalDateTime.now(),user));
        List<Order> order = orderDao.ordersByUserId(2L,2,1);
        System.out.println(order);
        assertEquals(2,order.size());
    }

    @Test
    @Transactional
    void deleteByID() {
        long id = 1L;
        orderDao.deleteByID(id);
        Optional<Order> deletedOrder = orderDao.getById(id);
        assertEquals(Optional.empty(),deletedOrder);
    }

    @Test
    void getById() {
        long id = 2L;
        Optional<Order> foundOrder = orderDao.getById(id);
        if (!foundOrder.isPresent()){
            throw new RuntimeException("No such order!");
        }
        Order order = foundOrder.get();
        assertEquals(id,order.getId());
    }

    @Test
    void getAll() {
        List<Order> orderList = orderDao.getAll(2,1);
        assertEquals(2,orderList.size());
    }

    @Test
    void ordersByUserId() {
        List<Order> order = orderDao.ordersByUserId(2L,3,1);
        System.out.println(order);
        assertEquals(1,order.size());
    }
}