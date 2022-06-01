package com.epam.esm.service.logic_service;

import com.epam.esm.configuration.AuditConfiguration;
import com.epam.esm.entity.*;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderLogicServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService<User> userLogicService;

    @Mock
    private AuditConfiguration auditConfiguration;

    @Mock
    private Pagination<Order> pagination;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private GiftCertificateService<GiftCertificate> giftCertificateLogicService;

    @InjectMocks
    private OrderLogicService orderLogicService;

    private static final User user = new User(1L,"AlexRendal","sss");
    private static final List<Tag> tagList = Collections.singletonList(new Tag("hello"));
    private static final GiftCertificate giftCertificate = new GiftCertificate(1L, "abc"
            , "abc", 50.00, 13,  tagList);
    private static Order order;
    private static final Role ROLE =  new Role(1L, "ROLE_ADMINISTRATOR");

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
       order.setId(1L);
       user.addRoleToUser(ROLE);
    }

    @Test
    void insertOrder() {
        Mockito.when(auditConfiguration.getCurrentUser()).thenReturn(user.getLogin());
        Mockito.when(userLogicService.findUserByUserLogin(user.getLogin())).thenReturn(user);
        Mockito.when(giftCertificateLogicService.getById(1L)).thenReturn(giftCertificate);
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        Order order1 = orderLogicService.insert(order);
        assertEquals(order,order1);
    }

    @Test
    void deleteOrder() {
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        orderLogicService.deleteByID(order.getId());
        Mockito.verify(orderRepository, Mockito.times(1)).delete(order);
    }

    @Test
    void getById() {
        Mockito.when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        Order foundOrder = orderLogicService.getById(order.getId());
        assertEquals(order,foundOrder);
    }

    @Test
    void getAll() {
        try {
            Page<Order> page = new PageImpl<>(new ArrayList<>(Collections.singletonList(order)));
            Mockito.when(auditConfiguration.getCurrentUser()).thenReturn(user.getLogin());
            Mockito.when(userLogicService.findUserByUserLogin("AlexRendal")).thenReturn(user);
            Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(ROLE));
            Mockito.when(pagination.checkHasContent(orderRepository.findAll(PageRequest.of(0,1))))
                    .thenReturn(page);
            Page<Order> orderList = orderLogicService.getAll(0,1);
            assertEquals(1L,orderList.getTotalElements());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    void ordersByUserId() {
        Page<Order> page = new PageImpl<>(new ArrayList<>(Collections.singletonList(order)));
        Mockito.when(userLogicService.getById(1L)).thenReturn(user);
        Mockito.when(pagination.checkHasContent(orderRepository.
                        findAllOrderWhereUserId(1L, PageRequest.of(1,1)))).thenReturn(page);
        Page<Order> orders = orderLogicService.ordersByUserId(1L,1,1);
        assertEquals(1,orders.getTotalElements());
    }
}