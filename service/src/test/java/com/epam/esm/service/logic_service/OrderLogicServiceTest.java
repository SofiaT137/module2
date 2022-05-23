package com.epam.esm.service.logic_service;

import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.OrderValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderLogicServiceTest {

    @Mock
    private OrderDaoImpl orderDao;

    @Mock
    private OrderValidator orderValidator;

    @Mock
    private UserService<User> userLogicService;

    @Mock
    private GiftCertificateService<GiftCertificate> giftCertificateLogicService;

    @InjectMocks
    private OrderLogicService orderLogicService;

    private static final User user = new User(1L,"AlexRendal","sss");
    private static final List<Tag> tagList = Collections.singletonList(new Tag("hello"));
    private static final GiftCertificate giftCertificate = new GiftCertificate(1L, "abc"
            , "abc", 50.00, 13, LocalDateTime.now(), LocalDateTime.now(), tagList);
    private static Order order;

    @BeforeEach
    void init(){
        orderLogicService.setUserLogicService(userLogicService);
        orderLogicService.setGiftCertificateLogicService(giftCertificateLogicService);
    }

    @BeforeAll
    static void fillTheOrderFields(){
       order = new Order();
       order.addUserToOrder(user);
       order.addGiftCertificateToOrder(giftCertificate);
       order.setPrice(giftCertificate.getPrice());
       order.setPurchaseTime(LocalDateTime.now());
       order.setId(1L);
    }

    @Test
    void insertOrder() {
        Mockito.when(userLogicService.getById(user.getId())).thenReturn(user);
        Mockito.when(giftCertificateLogicService.getById(1L)).thenReturn(giftCertificate);
        Mockito.when(orderDao.insert(order)).thenReturn(Optional.of(order));
        Order order1 = orderLogicService.insert(order);
        assertEquals(order,order1);
    }

    @Test
    void deleteOrder() {
        Mockito.when(orderDao.getById(1L)).thenReturn(Optional.of(order));
        orderLogicService.deleteByID(order.getId());
        Mockito.verify(orderDao, Mockito.times(1)).delete(order);
    }

    @Test
    void getById() {
        Mockito.when(orderDao.getById(order.getId())).thenReturn(Optional.of(order));
        Order foundOrder = orderLogicService.getById(order.getId());
        assertEquals(order,foundOrder);
    }

    @Test
    void getAll() {
        Mockito.when(orderDao.getAll(1,1)).thenReturn(Collections.singletonList(order));
        List<Order> orderList = orderLogicService.getAll(1,1);
        assertEquals(1,orderList.size());
    }

    @Test
    void ordersByUserId() {
        Mockito.when(userLogicService.getById(1L)).thenReturn(user);
        Mockito.when(orderDao.ordersByUserId(1L,1,1))
                .thenReturn(Collections.singletonList(order));
        List<Order> orderList = orderLogicService.ordersByUserId(1L,1,1);
        assertEquals(1,orderList.size());
    }
}