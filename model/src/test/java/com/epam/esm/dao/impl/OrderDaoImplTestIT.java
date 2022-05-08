package com.epam.esm.dao.impl;

import annotations.IT;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
class OrderDaoImplTestIT {

    private final OrderDao orderDao;
    private final UserDao userDao;

    private static final String CANNOT_INSERT_ORDER_EXCEPTION_MESSAGE = "Cannot insert this order";
    private static final String CANNOT_FIND_ORDER_EXCEPTION_MESSAGE = "Cannot find this order";
    private static final String CANNOT_FIND_USER_EXCEPTION_MESSAGE = "Cannot find this user";
    private static final Long ID = 2L;
    private static final Double NEW_PRICE = 500.18;
    private static final Integer PAGE_SIZE = 2;
    private static final Integer ORDERS_LIST_SIZE = 2;
    private static final Integer ORDERS_USER_LIST_SIZE = 1;
    private static final Integer PAGE_NUMBER = 1;


    @Autowired
    OrderDaoImplTestIT(OrderDao orderDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
    }

    @Test
    @Transactional
    void insert() {
        Optional<User> userDaoOptional = userDao.getById(ID);
        if (!userDaoOptional.isPresent()){
            throw new NoSuchElementException(CANNOT_FIND_USER_EXCEPTION_MESSAGE);
        }
        User user = userDaoOptional.get();
        orderDao.insert(new Order(NEW_PRICE, LocalDateTime.now(),user));
        List<Order> order = orderDao.ordersByUserId(ID,PAGE_SIZE,PAGE_NUMBER);
        System.out.println(order);
        assertEquals(ORDERS_LIST_SIZE,order.size());
    }

    @Test
    @Transactional
    void deleteByID() {
        orderDao.deleteByID(ID);
        Optional<Order> deletedOrder = orderDao.getById(ID);
        assertEquals(Optional.empty(),deletedOrder);
    }

    @Test
    void getById() {
        Optional<Order> foundOrder = orderDao.getById(ID);
        if (!foundOrder.isPresent()){
            throw new NoSuchElementException(CANNOT_FIND_ORDER_EXCEPTION_MESSAGE);
        }
        Order order = foundOrder.get();
        assertEquals(ID,order.getId());
    }

    @Test
    void getAll() {
        List<Order> orderList = orderDao.getAll(PAGE_SIZE,PAGE_NUMBER);
        assertEquals(ORDERS_LIST_SIZE,orderList.size());
    }

    @Test
    void ordersByUserId() {
        List<Order> order = orderDao.ordersByUserId(ID,PAGE_SIZE,PAGE_NUMBER);
        System.out.println(order);
        assertEquals(ORDERS_USER_LIST_SIZE,order.size());
    }
}